package com.tuto.aopdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class AopdemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AopdemoApplication.class, args);
		SortUtil aopUtil = context.getBean(SortUtil.class);

		final List<Integer> list = buildList(10000000);

		// aopUtil.bubbleSort(list.stream().mapToInt(Integer::intValue).toArray());
		// aopUtil.insertionSort(list.stream().mapToInt(Integer::intValue).toArray());
		// aopUtil.selectionSort(list.stream().mapToInt(Integer::intValue).toArray());
		aopUtil.mergeSort(list.stream().mapToInt(Integer::intValue).toArray());
		aopUtil.quickSort(list.stream().mapToInt(Integer::intValue).toArray());
		System.exit(0);

	}

	public static List<Integer> buildList(int random) {
		List<Integer> res = new ArrayList<>();
		int cpt = 0;

		while(cpt < random) {
			res.add(ThreadLocalRandom.current().nextInt(0, random));
			cpt++;
		}

		return res;
	}

}
