package com.example.curso.boot.web.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.curso.boot.domain.Cargo;
import com.example.curso.boot.service.CargoService;

@Component
public class StringToCargoConversor implements Converter<String, Cargo> {
	
	@Autowired
	private CargoService service; // faz consulta a partir do id 

	@Override
	public Cargo convert(String text) {
		if (text.isEmpty()) {
			return null;
		}
		Long id = Long.valueOf(text); // consulta o id na camada service e retorna o cargo.
		return service.buscarPorID(id);
	}

}
