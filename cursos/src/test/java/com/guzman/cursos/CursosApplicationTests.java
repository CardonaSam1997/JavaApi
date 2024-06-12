package com.guzman.cursos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CursosApplicationTests {

	@Test
	void contextLoads() {
	}


	//funciona
	@Test
	public void fibonaci(){
		int a=1, b=0, c=0;
		for(int i =0; i<9; i++){
			System.out.println(c);
			c = a+b;
			a=b;
			b=c;
		}
	}
	//lo mismo pero con recursividad
}
