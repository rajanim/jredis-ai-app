package org.example;

import com.redislabs.redisai.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        System.out.println( "Hello World!" );
        RedisAI client = new RedisAI("localhost", 6379);
        //System.out.println(client.getInfo("MODULE LIST"));
        String modelUri = "/Users/rajanishivarajmaski1/Redis/RedisAI/JRedisAI/src/test/resources/test_data/graph.pb";
        byte[] blob = Files.readAllBytes(Paths.get(modelUri));

        Model model = new Model(Backend.TF, Device.CPU, new String[] {"a", "b"}, new String[] {"mul"}, blob);
        client.setModel("model",  model);

        client.setTensor("a", new float[] {2, 3}, new int[]{2});
        client.setTensor("b", new float[] {2, 3}, new int[]{2});

        client.runModel("model", new String[] {"a", "b"}, new String[] {"c"});
        Tensor tensor = client.getTensor("c");
        float[] values = (float[]) tensor.getValues();
        for (float value : values) {
            System.out.print(value + " ");
        }

    }
}
