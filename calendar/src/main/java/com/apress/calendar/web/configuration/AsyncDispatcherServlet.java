package com.apress.calendar.web.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

@WebServlet(urlPatterns = {"/async/*"}, asyncSupported = true, name = "async")
public class AsyncDispatcherServlet extends DispatcherServlet {
    private ExecutorService exececutor;
    private static final int NUM_ASYNC_TASKS = 15;
    private static final long TIME_OUT = 10 * 1_000;

    private Logger log = LoggerFactory.getLogger(AsyncDispatcherServlet.class);


    @Override
    public void destroy() {
        exececutor.shutdownNow();
        super.destroy();
    }

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final AsyncContext ac = request.startAsync(request, response);
        ac.setTimeout(TIME_OUT);

        FutureTask task = new FutureTask(() -> {
            try{
                log.debug("Dispatching request " + request);
                AsyncDispatcherServlet.super.doDispatch(request, response);
                log.debug("doDispatch returned from processing request " + request);
                ac.complete();
            } catch (Exception ex){
                log.error("Error in async request", ex);
            }
        }, null);

        ac.addListener(new AsyncDispatcherServletListener(task));
        exececutor.execute(task);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        log.info("**** AsyncDispatcherServlet.init(): {}", config.getServletName());
        exececutor = Executors.newFixedThreadPool(NUM_ASYNC_TASKS);
    }

    private class AsyncDispatcherServletListener implements AsyncListener {

        private FutureTask future;

        public AsyncDispatcherServletListener(FutureTask future) {
            this.future = future;
        }

        @Override
        public void onTimeout(AsyncEvent event) throws IOException {
            logger.warn("Async request did not complete timeout occured");
            handleTimeoutOrError(event, "Request timed out");
        }

        @Override
        public void onComplete(AsyncEvent event) throws IOException {
            logger.debug("Completed async request");
        }

        @Override
        public void onError(AsyncEvent event) throws IOException {
            String error = (event.getThrowable() == null ? "UNKNOWN ERROR" : event.getThrowable().getMessage());
            logger.error("Error in async request " + error);
            handleTimeoutOrError(event, "Error processing " + error);
        }

        @Override
        public void onStartAsync(AsyncEvent event) throws IOException {
            logger.debug("Async Event started..");
        }

        private void handleTimeoutOrError(AsyncEvent event, String message) {
            PrintWriter writer = null;
            try {
                future.cancel(true);
                HttpServletResponse response = (HttpServletResponse) event.getAsyncContext().getResponse();
                //HttpServletRequest request = (HttpServletRequest) event.getAsyncContext().getRequest();
                //request.getRequestDispatcher("/app/error.htm").forward(request, response);
                writer = response.getWriter();
                writer.print(message);
                writer.flush();
            } catch (Exception ex) {
                   // FIXME: logger.error(ex);
            } finally {
                event.getAsyncContext().complete();
                if (writer != null) {
                    writer.close();
                }
            }
        }
    }
}
