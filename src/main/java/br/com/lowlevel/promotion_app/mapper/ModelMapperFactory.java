package br.com.lowlevel.promotion_app.mapper;

import br.com.lowlevel.promotion_app.data.vo.v1.BookVO;
import br.com.lowlevel.promotion_app.data.vo.v1.PersonVO;
import br.com.lowlevel.promotion_app.models.Book;
import br.com.lowlevel.promotion_app.models.Person;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class ModelMapperFactory {
    protected static ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.createTypeMap(Person.class, PersonVO.class).addMapping(Person::getId, PersonVO::setKey);
        modelMapper.createTypeMap(PersonVO.class, Person.class).addMapping(PersonVO::getKey, Person::setId);
        modelMapper.createTypeMap(Book.class, BookVO.class).addMapping(Book::getId, BookVO::setKey);
        modelMapper.createTypeMap(BookVO.class, Book.class).addMapping(BookVO::getKey, Book::setId);
    }

    public static <O, D> D parseObject(O origin, Class<D> destination) {

        return modelMapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
        List<D> destinationObjects = new ArrayList<>();
        for (O o : origin) destinationObjects.add(modelMapper.map(o, destination));
        return destinationObjects;
    }
}
