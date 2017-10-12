package com.haichecker.simple;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        File file = new File("/home/haichecker/project/android/LiMaApp/app/src/main/res/mipmap-xxxhdpi/");
//        for (File f :file.listFiles()) {
//            if (f.getName().contains("矩形"))
//            {
//                boolean isrename = f.delete();
////               boolean isrename = f.renameTo(new File(f.getParent() + "/" + f.getName().replaceAll("-","_")));
////                boolean isrename = f.renameTo(new File(f.getParent() + "/" + f.getName().toLowerCase()));
//                System.out.println("重命名："+isrename);
////                System.out.println(f.getParent() + "/" + f.getName().replaceAll("-","_"));
//            }
//        }
        assertEquals(4, 2 + 2);
    }
}
