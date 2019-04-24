package com.tianqiauto.textile.weaving.caiji.jintianju;

import io.github.biezhi.excel.plus.Reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName Test
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-04-23 15:59
 * @Version 1.0
 **/
public class Test {



    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
//        Path path = Paths.get("/Users/xingxiaoshuai/Downloads/117");


        File file = new File("/Users/xingxiaoshuai/Downloads/117/I_STATUS.CSV");

        System.out.println(file.length());

        List<Model_Current> list = Reader.create(Model_Current.class)
                .from(file)
                .start(0)
                .asList();

        System.out.println(list);




//        useParallelStream(path);
    }


    // java8 并行流
    static void useParallelStream(Path path) throws IOException {
        long begin = System.currentTimeMillis();
        if (Files.exists(path) && Files.isDirectory(path)) {
            Stream<Path> files = Files.list(path);
            files.parallel().forEach(p -> {
                if("I_STATUS 1.CSV".equals(p.toFile().getName())){


//                    try (BufferedReader reader = Files.newBufferedReader(p)) {
//                        String line = null;
//                        while ((line = reader.readLine()) != null) {
//                            System.out.println(line);
//                        }
//                    } catch (IOException e) {
//                    }
                }

            });
        }
        System.out.println("used:" + (System.currentTimeMillis() - begin) + "mills");
    }

}
