package com.example.demo;

import com.example.demo.exception.IngredientInsufficientException;
import com.example.demo.exception.IngredientNotFoundException;
import com.example.demo.exception.NoFreeOutletException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.BeverageService;
import com.example.demo.service.MachineService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DemoApplication {
	private MachineService machineService;
	private BeverageService beverageService;
	public static void main(String[] args) {
//		SpringApplication.run(DemoApplication.class, args);

		DemoApplication app = new DemoApplication();
		app.machineService = MachineService.getInstance();
		app.beverageService = BeverageService.getInstance();


		app.setupBeverages();

		app.testCase1();
		app.testCase2();
		app.testCase3();
		app.testCase4();


	}

	private void testCase1(){
		/*
			Simple scenario.
			3 outlets available
			3 beverages requested
			Sufficient ingredients available
		 */
		System.out.println("\n\n........Test 1........");
		Map<String,Integer> machine1Ingreds = new HashMap<>();
		machine1Ingreds.put("hot_water",1000);
		machine1Ingreds.put("hot_milk",1000);
		machine1Ingreds.put("ginger_syrup",100);
		machine1Ingreds.put("sugar_syrup",100);
		machine1Ingreds.put("tea_leaves_syrup",100);

		machineService.createMachine(3,machine1Ingreds);

		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(() -> serve(1,"hot_tea"));
		executor.execute(() -> serve(1,"hot_coffee"));
		executor.execute(() -> serve(1,"black_tea"));
		executor.shutdown();
		while(!executor.isTerminated());
	}

	private void testCase2(){
		/*
			3 outlets available
			4 beverages requested at same time
			expected one of them gets rejected
		 */
		System.out.println("\n\n........Test 2........");
		Map<String,Integer> machine1Ingreds = new HashMap<>();
		machine1Ingreds.put("hot_water",1000);
		machine1Ingreds.put("hot_milk",1000);
		machine1Ingreds.put("ginger_syrup",100);
		machine1Ingreds.put("sugar_syrup",100);
		machine1Ingreds.put("tea_leaves_syrup",100);

		machineService.createMachine(3,machine1Ingreds);

		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(() -> serve(2,"hot_tea"));
		executor.execute(() -> serve(2,"hot_coffee"));
		executor.execute(() -> serve(2,"black_tea"));
		executor.execute(() -> serve(2,"black_tea"));
		executor.shutdown();
		while(!executor.isTerminated());
	}
	private void testCase3(){
		/*
			3 outlets available
			3 beverages requested at same time
			but limited ingredients available
			expected one of them gets rejected due to shortage
		 */
		System.out.println("\n\n........Test 3........");
		Map<String,Integer> machine1Ingreds = new HashMap<>();
		machine1Ingreds.put("hot_water",500);
		machine1Ingreds.put("hot_milk",200);
		machine1Ingreds.put("ginger_syrup",100);
		machine1Ingreds.put("sugar_syrup",100);
		machine1Ingreds.put("tea_leaves_syrup",100);

		machineService.createMachine(3,machine1Ingreds);

		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(() -> serve(3,"hot_tea"));
		executor.execute(() -> serve(3,"hot_coffee"));
		executor.execute(() -> serve(3,"black_tea"));
		executor.shutdown();
		while(!executor.isTerminated());
	}

	private void testCase4(){
		/*
			2 outlets available
			3 beverages requested at same time
			sufficient ingredients available
			expected, one request gets rejected as only 2 outlets available
			4th beverage requested after a few seconds
			expected, 4th beverage prepared, as outlet has become free by now
		 */
		System.out.println("\n\n........Test 4........");
		Map<String,Integer> machine1Ingreds = new HashMap<>();
		machine1Ingreds.put("hot_water",600);
		machine1Ingreds.put("hot_milk",600);
		machine1Ingreds.put("ginger_syrup",100);
		machine1Ingreds.put("sugar_syrup",100);
		machine1Ingreds.put("tea_leaves_syrup",100);

		machineService.createMachine(2,machine1Ingreds);

		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(() -> serve(4,"hot_tea"));
		executor.execute(() -> serve(4,"hot_coffee"));
		executor.execute(() -> serve(4,"black_tea"));
		executor.execute(() -> {
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			serve(4,"black_tea");});
		executor.shutdown();
		while(!executor.isTerminated());
	}

	private void setupBeverages() {



		Map<String,Integer> hotTea = new HashMap<>();
		hotTea.put("hot_water",200);
		hotTea.put("hot_milk",100);
		hotTea.put("ginger_syrup",10);
		hotTea.put("sugar_syrup",10);
		hotTea.put("tea_leaves_syrup",30);

		Map<String,Integer> hotCoffee = new HashMap<>();
		hotCoffee.put("hot_water",100);
		hotCoffee.put("ginger_syrup",30);
		hotCoffee.put("hot_milk",400);
		hotCoffee.put("sugar_syrup",5);
		hotCoffee.put("tea_leaves_syrup",30);

		Map<String,Integer> blackTea = new HashMap<>();
		blackTea.put("hot_water",300);
		blackTea.put("ginger_syrup",30);
		blackTea.put("sugar_syrup",50);
		blackTea.put("tea_leaves_syrup",30);

		Map<String,Integer> greenTea = new HashMap<>();
		greenTea.put("hot_water",100);
		greenTea.put("ginger_syrup",30);
		greenTea.put("sugar_syrup",50);
		greenTea.put("green_mixture",30);

		beverageService.createBeverage("hot_tea",hotTea);
		beverageService.createBeverage("hot_coffee",hotCoffee);
		beverageService.createBeverage("black_tea",blackTea);
		beverageService.createBeverage("green_tea",greenTea);
	}

	private void serve(int machineId, String beverageName){
		try{
			machineService.serveBeverage(machineId,beverageName);
			System.out.println(beverageName + " is prepared");
		}catch (ResourceNotFoundException e){
			System.out.println(e.getMessage());
		}catch (IngredientInsufficientException e){
			System.out.println(beverageName + " cannot be prepared because " + e.getIngredient() +" is not sufficient");
		}catch (IngredientNotFoundException e){
			System.out.println(beverageName + " cannot be prepared because " + e.getIngredient() +" is not available");
		}catch (NoFreeOutletException e){
			System.out.println(beverageName + " cannot be prepared now. " + e.getMessage());
		}
	}
}
