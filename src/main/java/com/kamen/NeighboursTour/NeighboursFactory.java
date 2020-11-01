package com.kamen.NeighboursTour;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource("classpath:neighbours.properties")
public class NeighboursFactory implements NeighboursFactoryInterface {

    @Autowired
    private Environment env;

    @Override
    public List<String> getNeighbours(String country) {
        List<String> neighbours = env.getProperty("neighbours." + country, List.class);

        return neighbours == null ? new ArrayList<>(): neighbours;
    }

}
