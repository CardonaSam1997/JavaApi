package com.guzman.cursos;

import com.guzman.cursos.models.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

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

	@Test
	public void pruebaList(){
		List<Usuario> p = new ArrayList<>();
		p.add(new Usuario("juan"));
		p.add(new Usuario("pedro"));

		System.out.println(p.get(1));
	}
}
