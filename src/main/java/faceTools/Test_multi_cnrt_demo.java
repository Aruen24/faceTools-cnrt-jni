//package faceTools;
//
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Vector;
//import java.util.concurrent.CountDownLatch;
//
//public class Test_multi_cnrt_demo extends Thread{
//    private int number;
//    private FaceTools net;
//    private CountDownLatch latch;
//    private List<String> fileResult;
//    private int startIndex;
//    private int endIndex;
//    private int picSize;
//    private long new_sess;
////    private String result_path;
//
//    public static byte[] image2ByteArr(BufferedImage img) {
//        int w = img.getWidth();
//        int h = img.getHeight();
//        byte[] rgb = new byte[w*h*3];
//        for (int i = 0; i < h; i++) {
//            for (int j = 0; j < w; j++) {
//                int val = img.getRGB(j, i);
//                int red = (val >> 16) & 0xFF;
//                int green = (val >> 8) & 0xFF;
//                int blue = val & 0xFF;
//
//                rgb[(i*w+j)*3] = (byte) red;
//                rgb[(i*w+j)*3+1] = (byte) green;
//                rgb[(i*w+j)*3+2] = (byte) blue;
//                //System.out.println(String.valueOf((i*h+j)*3));
//            }
//        }
//        return rgb;
//    }
//
//    public static int[][][] image2FloatArr(BufferedImage img) {
//        int w = img.getWidth();
//        int h = img.getHeight();
//        int[][][] floatValues = new int[w][h][3];
//        for (int i = 0; i < h; i++) {
//            for (int j = 0; j < w; j++) {
//                int val = img.getRGB(j, i);
//                floatValues[j][i][0] = (val >> 16) & 0xFF;
//                floatValues[j][i][1] = (val >> 8) & 0xFF;
//                floatValues[j][i][2] = val & 0xFF;
//                //System.out.println(Arrays.toString(floatValues[j][i]));
//            }
//        }
//        return floatValues;
//    }
//
//
//    /**
//     * 图片在多级目录下处理
//     * @param picPath
//     * @return
//     * @auther Aruen
//     * @date 2020.10.30
//     */
//    public static List<String> multifile(String picPath, int pic_batch){
//        List<String> files = new ArrayList<String>();
//        File file = new File(picPath);
//        if(file!=null){// 判断对象是否为空
//            for (int h = 0; h < pic_batch; h++) {
//                if (!file.isFile()) {
//                    File[] tempList = file.listFiles();// 列出全部的文件
//
//                    for (int i = 0; i < tempList.length; i++) {
//                        if (tempList[i].isDirectory()) {// 如果是目录，二级目录
//                            File[] p_path = tempList[i].listFiles();// 列出全部的文件
//                            for (int j = 0; j < p_path.length; j++) {
//                                if (p_path[j].isDirectory()) {// 如果是目录,三级目录
//                                    File[] p_path_result = p_path[j].listFiles();// 列出全部的文件
//                                    for (int k = 0; k < p_path_result.length; k++) {
//                                        files.add(p_path_result[k].toString());
//                                    }
//
//                                } else if (p_path[j].isFile()) {
//                                    files.add(p_path[j].toString());// 如果不是目录，输出路径
//                                }
//                            }
//                        } else if (tempList[i].isFile()) {
//                            files.add(tempList[i].toString());// 如果不是目录，输出路径
//                        }
//                    }
//
//
//                } else {
//                    files.add(file.toString());// 如果不是目录，输出路径
//                }
//            }
//            return files;
//        }else{
//            return null;
//        }
//    }
//
//
//    /**
//     * resize图片大小
//     * @param src
//     * @param newWidth
//     * @param newHeight
//     * @return
//     * @throws IOException
//     * @auther Aruen
//     * @date 2020.10.29
//     */
//    public static BufferedImage resize(String src,int newWidth,int newHeight) {
//        try {
//            File srcFile = new File(src);
//            BufferedImage img = ImageIO.read(srcFile);
//            int w = img.getWidth();
//            int h = img.getHeight();
//            BufferedImage dimg = new BufferedImage(newWidth, newHeight, img.getType());
//            Graphics2D g = dimg.createGraphics();
//            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//            g.drawImage(img, 0, 0, newWidth, newHeight, 0, 0, w, h, null);
//            g.dispose();
////            ImageIO.write(dimg, "jpg", toFile);
//            return dimg;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 获得处理结果
//     * @param modelPath
//     * @param picSize
//     * @param fileResult
//     **@auther Aruen
//     * @date 2020.10.30
//     */
//    public static void getResult(String modelPath, int picSize, List<String> fileResult){
//        FaceTools net = new FaceTools(modelPath, 0, 0);
//        int count = 0;
//        for(String img_name : fileResult) {
//            BufferedImage bi = null;
//
//            try {
////            bi = ImageIO.read(file);
//                bi = resize(img_name, picSize, picSize);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            byte[] rgb = image2ByteArr(bi);
//            long startTime = System.currentTimeMillis();
//            float[][] output = net.defaultForward(rgb, bi.getWidth(), bi.getHeight(), 0);
//            long endTime = System.currentTimeMillis();
//            for (int i = 0; i < output.length; i++) {
//                System.out.println ( Arrays.toString (output[i]));
//            }
//            count++;
//            System.out.println("The model deal time："+(endTime-startTime)+"ms******"+"deal picture count："+count+"----picpath："+img_name);
//        }
//        net.release();
//    }
//
//    public Test_multi_cnrt_demo(FaceTools net, int number, CountDownLatch latch, List<String> fileResult, int picSize, long new_sess, int startIndex, int endIndex)
//    {
//        this.net = net;
//        this.number = number;
//        this.latch = latch;
//        this.fileResult = fileResult;
//        this.startIndex = startIndex;
//        this.endIndex = endIndex;
//        this.picSize = picSize;
//        this.new_sess = new_sess;
////        this.result_path = result_path;
//    }
//
//    public void run()
//    {
//        try {
//            BufferedImage img_buff=null;
//            Vector<Box> boxes=null;
////            net.setCPUCore(number);
//
//            //输出结果到指定文件路径
////            PrintStream ps = new PrintStream(result_path);
////            System.setOut(ps);//把创建的打印输出流赋给系统。即系统下次向 ps输出
//            List<String> subList = fileResult.subList(startIndex, endIndex);
//            int count = 0;
//            long avg_t = 0;
//            long total_t = 0;
//            for(String img_name : subList) {
//                BufferedImage bi = null;
//
//                try {
////            bi = ImageIO.read(file);
//                    bi = resize(img_name, picSize, picSize);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                byte[] rgb = image2ByteArr(bi);
//                long startTime = System.currentTimeMillis();
//                float[][] new_output = net.forward(rgb, bi.getWidth(), bi.getHeight(), 0, new_sess);
//                long endTime = System.currentTimeMillis();
//
////                for (int i = 0; i < new_output.length; i++) {
////                    System.out.println ( Arrays.toString (new_output[i]));
////                }
//                avg_t = endTime - startTime;
//                total_t += avg_t;
//                count++;
//                System.out.println("current thread name："+Thread.currentThread().getName()+"----model calcu time："+(endTime-startTime)+"ms******"+"deal pictures count："+count+"----picpath："+img_name);
//            }
//            System.out.println("model average time："+(total_t/subList.size())+"ms");
//            net.releaseNewSession(new_sess);
//        } finally {
//            if (latch != null) {
//                latch.countDown();
//            }
//        }
//    }
//
//
//    public static void main(String args[]) throws InterruptedException {
//        // 模型路径
//        String model_path = args[0];
//        //处理图片路径
//        String picture_path = args[1];
//        //图片resize大小
//        int picSize = Integer.parseInt(args[2]);
//
//
//        //初始线程数
//        String thread_num = args[3];
//
//        //图片batch
//        int pic_batch = Integer.parseInt(args[4]);
//
//        int num = Integer.parseInt(thread_num);
//
//        List<String> fileResult = multifile(picture_path, pic_batch);
//        int length = fileResult.size();
//
//        int baseNum = length / num;
//        int remainderNum = length % num;
//        int end  = 0;
//        //创建推理对象
//        FaceTools net = new FaceTools(model_path, 0, 0);
//
//        CountDownLatch latch = new CountDownLatch(num);//初始化countDown
//        long startTime = System.currentTimeMillis();
//        for (int i = 0; i < num; i++) {
//            int start = end ;
//            end = start + baseNum;
//            if(i == (num-1)){
//                end = length;
//            }else if( i < remainderNum){
//                end = end + 1;
//            }
//            long new_sess = net.forkSession();
//
//            Thread thread = new Test_multi_cnrt_demo(net, i, latch, fileResult, picSize, new_sess, start , end);
//            thread.start();
//        }
//
//        latch.await();//等待所有线程完成工作
//        net.release(); //所有线程完成后释放c++对象
//        long endTime = System.currentTimeMillis();
//        long t1 = endTime - startTime;
//    	System.out.println("total_time:"+t1+"ms");
//    }
//}











package faceTools;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Test_multi_cnrt_demo extends Thread{
    private int rotation;
    private FaceTools face_tools;
    private int number;
    private FaceTools modelPath;
    private CountDownLatch latch;
    private List<String> fileResult;
    private int startIndex;
    private int endIndex;
    private int picSize;
    private long cnrt_facedetect_sess;
    private long cnrt_facetrack_sess;
    private long cnrt_facefeature_sess;
    private long cnrt_yolov3_sess;
    private long cnrt_pose_sess;
    private long cnrt_bodydetect_sess;
    private long cnrt_bodytrack_sess;
    private long cnrt_facemaskdetect_sess;
    private long cnrt_body_attr_sess;
//    private String result_path;

    public static byte[] image2ByteArr(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        byte[] rgb = new byte[w*h*3];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int val = img.getRGB(j, i);
                int red = (val >> 16) & 0xFF;
                int green = (val >> 8) & 0xFF;
                int blue = val & 0xFF;

                rgb[(i*w+j)*3] = (byte) red;
                rgb[(i*w+j)*3+1] = (byte) green;
                rgb[(i*w+j)*3+2] = (byte) blue;
                //System.out.println(String.valueOf((i*h+j)*3));
            }
        }
        return rgb;
    }

    public static int[][][] image2FloatArr(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        int[][][] floatValues = new int[w][h][3];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int val = img.getRGB(j, i);
                floatValues[j][i][0] = (val >> 16) & 0xFF;
                floatValues[j][i][1] = (val >> 8) & 0xFF;
                floatValues[j][i][2] = val & 0xFF;
                //System.out.println(Arrays.toString(floatValues[j][i]));
            }
        }
        return floatValues;
    }


    /**
     * 图片在多级目录下处理
     * @param picPath
     * @return
     * @auther Aruen
     * @date 2020.10.30
     */
    public static List<String> multifile(String picPath){
        List<String> files = new ArrayList<String>();
        File file = new File(picPath);
        if(file!=null){// 判断对象是否为空
            for (int h = 0; h < 1; h++) {
                if(!file.isFile()){
                    File[] tempList = file.listFiles() ;// 列出全部的文件
                    for(int i=0;i<tempList.length;i++){
                        if(tempList[i].isDirectory()){// 如果是目录，二级目录
                            File[] p_path = tempList[i].listFiles() ;// 列出全部的文件
                            for(int j=0;j<p_path.length;j++){
                                if(p_path[j].isDirectory()){// 如果是目录,三级目录
                                    File[] p_path_result = p_path[j].listFiles() ;// 列出全部的文件
                                    for(int k=0;k<p_path_result.length;k++){
                                        files.add(p_path_result[k].toString());
                                    }

                                }else if(p_path[j].isFile()){
                                    files.add(p_path[j].toString());// 如果不是目录，输出路径
                                }
                            }
                        }else if(tempList[i].isFile()){
                            files.add(tempList[i].toString());// 如果不是目录，输出路径
                        }
                    }
                }else{
                    files.add(file.toString());// 如果不是目录，输出路径
                }
            }
            return files;
        }else{
            return null;
        }
    }


    /**
     * resize图片大小
     * @param src
     * @param newWidth
     * @param newHeight
     * @return
     * @throws IOException
     * @auther Aruen
     * @date 2020.10.29
     */
    public static BufferedImage resize(String src,int newWidth,int newHeight) {
        try {
            File srcFile = new File(src);
            BufferedImage img = ImageIO.read(srcFile);
            int w = img.getWidth();
            int h = img.getHeight();
            BufferedImage dimg = new BufferedImage(newWidth, newHeight, img.getType());
            Graphics2D g = dimg.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(img, 0, 0, newWidth, newHeight, 0, 0, w, h, null);
            g.dispose();
//            ImageIO.write(dimg, "jpg", toFile);
            return dimg;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获得处理结果
     //     * @param modelPath
     * @param picSize
     * @param fileResult
     **@auther Aruen
     * @date 2020.10.30
     */
//    public static void getResult(String modelPath, int picSize, List<String> fileResult){
//        FaceTools net = new FaceTools(modelPath, 0);
//        int count = 0;
//        for(String img_name : fileResult) {
//            BufferedImage bi = null;
//
//            try {
//            bi = ImageIO.read(new File(img_name));
////                bi = resize(img_name, picSize, picSize);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            byte[] rgb = image2ByteArr(bi);
//            long startTime = System.currentTimeMillis();
//            float[][] output = net.defaultForward(rgb, bi.getWidth(), bi.getHeight(), 0);
//            long endTime = System.currentTimeMillis();
//            for (int i = 0; i < output.length; i++) {
//                System.out.println ( Arrays.toString (output[i]));
//            }
//            count++;
//            System.out.println("模型计算耗时："+(endTime-startTime)+"ms******"+"处理图片数："+count+"----图片路径："+img_name);
//        }
//        net.release();
//    }



    //人脸口罩识别
    //public Test_multi_cnrt_demo(FaceTools face_tools, long cnrt_facemaskdetect_sess, int number, CountDownLatch latch, List<String> fileResult,  int rotation, int picSize, int startIndex, int endIndex)
    //追踪
//    public Test_multi_cnrt_demo(FaceTools_back face_tools, long cnrt_facetrack_sess, long cnrt_facedetect_sess, int number, CountDownLatch latch, List<String> fileResult, int rotation, int picSize, int startIndex, int endIndex)
    //检测特征提取
    //public Test_multi_cnrt_demo(FaceTools face_tools, long cnrt_yolov3_sess, int number, CountDownLatch latch, List<String> fileResult,  int rotation, int picSize, int startIndex, int endIndex)
    //人体关键点检测yolov3+pose_model
    //public Test_multi_cnrt_demo(FaceTools face_tools, long cnrt_yolov3_sess, long cnrt_pose_sess, int number, CountDownLatch latch, List<String> fileResult,  int rotation, int picSize, int startIndex, int endIndex)
    //人体关键点检测追踪retina+pose_model
    //public Test_multi_cnrt_demo(FaceTools face_tools, long cnrt_bodydetect_sess, long cnrt_bodytrack_sess, int number, CountDownLatch latch, List<String> fileResult,  int rotation, int picSize, int startIndex, int endIndex)
    //public Test_multi_cnrt_demo(FaceTools face_tools, long cnrt_bodydetect_sess, long cnrt_bodytrack_sess, long cnrt_pose_sess, int number, CountDownLatch latch, List<String> fileResult,  int rotation, int picSize, int startIndex, int endIndex)
    //人体属性检测
    public Test_multi_cnrt_demo(FaceTools face_tools, long cnrt_body_attr_sess, int number, CountDownLatch latch, List<String> fileResult,  int rotation, int picSize, int startIndex, int endIndex)
    {
        this.face_tools = face_tools;
        this.number = number;
        this.latch = latch;
        this.fileResult = fileResult;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.picSize = picSize;
        this.cnrt_facedetect_sess = cnrt_facedetect_sess;
        this.cnrt_facetrack_sess = cnrt_facetrack_sess;
        this.cnrt_facefeature_sess = cnrt_facefeature_sess;
        this.cnrt_yolov3_sess = cnrt_yolov3_sess;
        this.cnrt_pose_sess = cnrt_pose_sess;
        this.rotation = rotation;
        this.cnrt_bodydetect_sess = cnrt_bodydetect_sess;
        this.cnrt_bodytrack_sess = cnrt_bodytrack_sess;
        this.cnrt_facemaskdetect_sess = cnrt_facemaskdetect_sess;
        this.cnrt_body_attr_sess = cnrt_body_attr_sess;
//        this.result_path = result_path;
    }

    public void run()
    {
        try {
            BufferedImage img_buff=null;
//            net.setCPUCore(number);

            //输出结果到指定文件路径
//            PrintStream ps = new PrintStream(result_path);
//            System.setOut(ps);//把创建的打印输出流赋给系统。即系统下次向 ps输出
            List<String> subList = fileResult.subList(startIndex, endIndex);
            int count = 0;
            long avg_t = 0;
            long total_t = 0;


            for(String img_name : subList) {
                BufferedImage bi = null;

                try {
                    bi = ImageIO.read(new File(img_name));
//                    bi = resize(img_name, picSize, picSize);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                byte[] rgb = image2ByteArr(bi);
                long startTime = System.currentTimeMillis();


                //人脸口罩识别，传入的人脸检测出来的人脸框图片112*112  0-mask 1-nomask
                //long result = face_tools.cnrtMaskDetectForwardRect(rgb, bi.getWidth(), bi.getHeight(), rotation, 112, 0, cnrt_facemaskdetect_sess);
                //人脸口罩识别，输入原图、人脸框位置  0-mask 1-nomask
//                long result = face_tools.cnrtMaskDetectForward(rgb, bi.getWidth(), bi.getHeight(), 69, 48, 112, 112,rotation, 112, 0, cnrt_facemaskdetect_sess);
//                long endTime = System.currentTimeMillis();
//                if(result == 0){
//                    System.out.println("mask");
//                }else{
//                    System.out.println("nomask");
//                }






                //追踪，更新和获取人脸
//                face_tools.trackingUpdate(rgb, cnrt_facetrack_sess, cnrt_facedetect_sess, bi.getWidth(), bi.getHeight(), rotation, picSize);
//                Vector<Box> boxes = face_tools.getTrackFaces(rotation);
//                 //追踪、检测结果输出
//                for (int i = 0; i < boxes.size(); i++) {
//                    int[] box = boxes.get(i).box;
//                    float score_value = boxes.get(i).score;
//                    float[] angle_value = boxes.get(i).angles;
//                    System.out.println("left:"+box[0]+","+"top:"+box[1]+","+"right:"+box[2]+","+"bottom:"+box[3]);
//                }

                //检测，推理获得结果
//                Vector<Box> boxes = face_tools.cnrtFaceDetectByRgb(rgb, bi.getWidth(), bi.getHeight(), rotation, picSize, 0, cnrt_facedetect_sess);
//                for (int i = 0; i < boxes.size(); i++) {
//                    int[] box = boxes.get(i).box;
//                    float score_value = boxes.get(i).score;
//                    float[] angle_value = boxes.get(i).angles;
//                    System.out.println("left:"+box[0]+","+"top:"+box[1]+","+"right:"+box[2]+","+"bottom:"+box[3]);
//                }


                //人体检测追踪
//                float[][] result = face_tools.cnstreamTrackBodys(rgb, cnrt_yolov3_sess, bi.getWidth(), bi.getHeight(), rotation);
//                long endTime = System.currentTimeMillis();
//                System.out.println("检测目标个数："+result.length);
//                for (int i = 0; i < result.length; i++) {
//                    int left = (int)result[i][0];
//                    int top = (int)result[i][1];
//                    int right = (int)result[i][2];
//                    int bottom = (int)result[i][3];
//                    int index = (int)result[i][4];
//                    //float score = result[i][5];
//                    System.out.println("left:"+left+","+"top:"+top+","+"right:"+right+","+"bottom:"+bottom+","+"index:"+index);
//                }


                //特征提取,输入原图和人脸框位置pos_x=212, pos_y=48, pos_w=252, pos_h=252,推理获得结果
//                float[] feature = face_tools.cnrtForwardByFeature(rgb, bi.getWidth(), bi.getHeight(), 212, 48, 252, 252, rotation,picSize, 0, 1, cnrt_facefeature_sess); //type_mask 0-mask  1-nomask
//                //特征提取，输入截取的人脸框图片，推理获得结果
//                float[] feature = face_tools.cnrtForwardByRectFeature(rgb, bi.getWidth(), bi.getHeight(), rotation, picSize, 0, cnrt_facefeature_sess);
//                for (int i = 0; i < feature.length; i++) {
//                    System.out.println(feature[i]);
//                }
//                float score = face_tools.compare(feature1, feature2);



                //YoloV3目标检测，
//                float[][] result = face_tools.cnrtObjectDetectByYoloV3Rgb(rgb, bi.getWidth(), bi.getHeight(), rotation, picSize, 0, cnrt_yolov3_sess);
//                long endTime = System.currentTimeMillis();
//                System.out.println("检测目标个数："+result.length);
//                for (int i = 0; i < result.length; i++) {
//                    int left = (int)result[i][0];
//                    int top = (int)result[i][1];
//                    int right = (int)result[i][2];
//                    int bottom = (int)result[i][3];
//                    int index = (int)result[i][4];
//                    float score = result[i][5];
//                    System.out.println("left:"+left+","+"top:"+top+","+"right:"+right+","+"bottom:"+bottom+","+"index:"+index+","+"score:"+score);
//                }

                //人体关键点检测yolov3+pose_model
//                float[][] result = face_tools.cnrtForwardByYoloV3AlphaPose(rgb, bi.getWidth(), bi.getHeight(), rotation, picSize, 0, cnrt_yolov3_sess, cnrt_pose_sess);
//                long endTime = System.currentTimeMillis();
////                int []value = new int[51];
//                for (int i = 0; i < result.length; i++) {
//                    for(int j = 0; j < 51; j++){
////                        value[j] = (int)result[i][j];
//                        if((j+1) %3==0 ){
//                            System.out.println("img_score："+result[i][j]);
//                        }else{
//                            System.out.println("img_point："+result[i][j]);
//                        }
//
//                    }
////                    System.out.println("left:"+left+","+"top:"+top+","+"right:"+right+","+"bottom:"+bottom+","+"index:"+index+","+"score:"+score);
//                }



//                //追踪，更新和获取人体框retina+pose_model
//                face_tools.bodyTrackingUpdate(rgb, cnrt_bodytrack_sess, cnrt_bodydetect_sess, bi.getWidth(), bi.getHeight(), rotation);
//                float[][] result = face_tools.getTrackBodys(rotation);
//                long endTime = System.currentTimeMillis();
//                //追踪、检测结果输出
//                for (int i = 0; i < result.length; i++) {
//                    int left = (int)result[i][0];
//                    int top = (int)result[i][1];
//                    int right = (int)result[i][2];
//                    int bottom = (int)result[i][3];
//                    int index = (int)result[i][4];
//                    float score = result[i][5];
//                    System.out.println("人体框坐标信息： "+"left:"+left+","+"top:"+top+","+"right:"+right+","+"bottom:"+bottom+","+"index:"+index+","+"score:"+score);
//                }
//
//
//                //pose model人体关键点检测
//                float[][] result_point =  face_tools.cnrtForwardByAlphaPose(rgb, bi.getWidth(), bi.getHeight(), rotation, cnrt_pose_sess, result);
//                //追踪、检测结果输出
//                for (int i = 0; i < result_point.length; i++) {
//                    for(int j = 0; j < 51; j++){
////                        value[j] = (int)result[i][j];
//                        if((j+1) %3==0 ){
//                            System.out.println("pose model img_score："+result_point[i][j]);
//                        }else{
//                            System.out.println("pose model img_point："+result_point[i][j]);
//                        }
//
//                    }
////                    System.out.println("left:"+left+","+"top:"+top+","+"right:"+right+","+"bottom:"+bottom+","+"index:"+index+","+"score:"+score);
//                }


                //人体属性检测cnrt_body_attr_sess，网络输入192*256
                //String[] description = {"Hat", "Glasses", "ShortSleeve", "LongSleeve", "UpperStride", "UpperLogo", "UpperPlaid", "UpperSplice", "LowerStripe",
                //        "LowerPattern", "LongCoat", "Trousers", "Shorts", "Skirt&Dress", "boots", "HandBag", "ShoulderBag", "Backpack", "HoldObjectsInFront", "AgeOver60",
                //        "Age18-60", "AgeLess18", "Female", "Front", "Side", "Back"};
//                String[] description = {"帽子", "眼镜", "短袖", "长袖", "UpperStride", "UpperLogo", "UpperPlaid", "UpperSplice", "LowerStripe",
//                        "LowerPattern", "长外套", "裤子", "短裤", "裙子和连衣裙", "靴子", "手提包", "单肩包", "背包", "前面抱个物体", "年龄大于60",
//                        "年龄在18-60", "年龄小于18", "女性", "正面", "侧面", "背面"};
//                //float[][] result = face_tools.cnrtBodyAttrForwardRectRgb(rgb, bi.getWidth(), bi.getHeight(), rotation, 192, 256, 0, cnrt_body_attr_sess);//传入小图
//                float[][] result = face_tools.cnrtBodyAttrForwardRgb(rgb, bi.getWidth(), bi.getHeight(), 0, 0, bi.getWidth(), bi.getHeight(), rotation, 192, 256, 0, cnrt_body_attr_sess);//传入大图和小图坐标
//                long endTime = System.currentTimeMillis();
//                System.out.println("检测属性个数："+result.length);
//                for (int i = 0; i < result.length; i++) {
//                    int index = (int)result[i][0];
//                    float score = result[i][1];
//                    //float score = result[i][5];
//                    System.out.println("index:"+description[index]+","+"score:"+score);
//                }


                //多batch人体属性检测cnrt_body_attr_sess，网络输入192*256
                float[][] arrRect = {{0, 0, bi.getWidth(), bi.getHeight()},{0, 0, bi.getWidth(), bi.getHeight()},{0, 0, bi.getWidth(), bi.getHeight()},{0, 0, bi.getWidth(), bi.getHeight()},{0, 0, bi.getWidth(), bi.getHeight()},{0, 0, bi.getWidth(), bi.getHeight()}};
                String[] description = {"帽子", "眼镜", "短袖", "长袖", "UpperStride", "UpperLogo", "UpperPlaid", "UpperSplice", "LowerStripe",
                        "LowerPattern", "长外套", "裤子", "短裤", "裙子和连衣裙", "靴子", "手提包", "单肩包", "背包", "前面抱个物体", "年龄大于60",
                        "年龄在18-60", "年龄小于18", "女性", "正面", "侧面", "背面"};
                float[][] result = face_tools.cnrtBodyAttrForwardResultsRgb(rgb, bi.getWidth(), bi.getHeight(), rotation, 192, 256, 0,  cnrt_body_attr_sess, arrRect);
                long endTime = System.currentTimeMillis();
                System.out.println("检测目标个数："+result.length);
                for (int i = 0; i < result.length; i++) {//result.length 人体目标数
                    System.out.println("第"+i+"个人检测属性结果：");
                    for(int j = 0; j < 26; j++){
                        if(result[i][j] > 0.5){//检测到的属性
                            System.out.println("index:"+description[j]+","+"score:"+result[i][j]);
                        }
                    }
                }




                total_t += avg_t;
                count++;
                System.out.println("当前线程名字："+Thread.currentThread().getName()+"----模型计算耗时："+(endTime-startTime)+"ms******"+"处理图片数："+count+"----图片路径："+img_name);
            }
            System.out.println("模型平均耗时："+(total_t/subList.size())+"ms");
//            face_tools.cnrtSessionRelease(cnrt_facetrack_sess);
//            face_tools.cnrtSessionRelease(cnrt_facedetect_sess);
            //检测，特征提取
//            face_tools.cnrtSessionRelease(cnrt_facedetect_sess);
            //物体检测
            //face_tools.cnrtSessionRelease(cnrt_yolov3_sess);
            //人体关键点检测yolov3+pose_model
            //face_tools.cnrtSessionRelease(cnrt_yolov3_sess);
            //face_tools.cnrtSessionRelease(cnrt_pose_sess);

            //人体关键点检测追踪retina+pose_model
//            face_tools.cnrtSessionRelease(cnrt_bodydetect_sess);
//            face_tools.cnrtSessionRelease(cnrt_bodytrack_sess);
            //人脸口罩识别
            //face_tools.cnrtSessionRelease(cnrt_facemaskdetect_sess);
            //face_tools.cnrtSessionRelease(cnrt_pose_sess);

            //人体属性检测
            face_tools.cnrtSessionRelease(cnrt_body_attr_sess);

        } finally {
            if (latch != null) {
                latch.countDown();
            }
        }
    }


    public static void main(String args[]) throws InterruptedException {
        // 模型路径
        String model_path = args[0];
        //处理图片路径
        String picture_path = args[1];
        //图片resize大小
        int picSize = Integer.parseInt(args[2]);

        List<String> fileResult = multifile(picture_path);
        int length = fileResult.size();

        //初始线程数
        String thread_num = args[3];
        int num = Integer.parseInt(thread_num);

        //const int ROTATION_0 = 1;
        //const int ROTATION_90 = 2;
        //const int ROTATION_180 = 4;
        //const int ROTATION_270 = 8;
        int rotation = Integer.parseInt(args[4]);

        //第一帧图片路径
        String first_picture_path = args[5];

        int baseNum = length / num;
        int remainderNum = length % num;
        int end  = 0;
        //创建推理对象
        FaceTools face_tools = new FaceTools(model_path, 1); //0-retinaface+pose      1-yolov3+pose,只对这个组合有效

        //人脸口罩检测输入112*112人脸图片
        //long cnrt_facemaskdetect = face_tools.cnrtNetInit("mobilev2_mask_detect_112_int8_b1_c4.cambricon", 0, 0);

        //追踪，创建加载模型
//        long cnrt_facedetect = face_tools.cnrtNetInit("retinaface_mobile0.25_intx_1_1_int8.cambricon", 0, 0);
////        long cnrt_facetrack = face_tools.cnrtNetInit("onet_int8_b1_c1.cambricon", 0, 0);
//        long cnrt_facetrack = face_tools.cnrtNetInit("retinaface_mobile0.25_intx_1_1_int8.cambricon", 0, 0);

        //检测，创建加载模型
//        long cnrt_facedetect = face_tools.cnrtNetInit("retinaface_mobile0.25_intx_1_1_int8.cambricon", 0, 0);

        //提取特征，创建加载模型 resnet101_int16_b1_c1.cambricon
//        long cnrt_facefeature = face_tools.cnrtNetInit("vargfacenet_int8_b1_c1.cambricon", 0, 0);

        //yoloV3目标检测，创建加载模型 yolov3_lu_1batch_4core_int8_270.cambricon/yolov3_int8_220_core1_batch1.cambricon     yolov4_smoke_call_intx_1_1_int8.cambricon
        //long cnrt_object_detect = face_tools.cnrtNetInit("yolov3_tiny_intx_4_1.cambricon", 0, 0);
        //long cnrt_object_detect = face_tools.cnrtNetInit("yolov3_lu_1batch_4core_int8_270.cambricon", 0, 0);

        //人体关键点检测yolov3+pose_model    retinal+pose_model
        //long cnrt_object_detect = face_tools.cnrtNetInit("yolov3_int8_body_1_1.cambricon", 0, 0);
        //long cnrt_object_detect = face_tools.cnrtNetInit("retina_220_416_234.cambricon", 0, 0);
        //long cnrt_pose_detect = face_tools.cnrtNetInit("pose_int8_f16.cambricon", 0, 0);

        //人体关键点检测retinal+追踪
//        long cnrt_bodydetect= face_tools.cnrtNetInit("retina_220_416_234.cambricon", 0, 0);
//        long cnrt_bodytrack= face_tools.cnrtNetInit("retina_220_416_234.cambricon", 0, 0);
        //long cnrt_bodydetect= face_tools.cnrtNetInit("yolov3_int8_body_1_1.cambricon", 0, 0);
        //long cnrt_bodydetect= face_tools.cnrtNetInit("yolov3_tiny_intx_4_1.cambricon", 0, 0);
        //long cnrt_bodytrack = face_tools.cnrtNetInit("yolov3_tiny_intx_4_1.cambricon", 0, 0);
        //long cnrt_bodytrack = face_tools.cnrtNetInit("retina_220_256_96.cambricon", 0, 0);
        //long cnrt_pose_model = face_tools.cnrtNetInit("pose_int8_f16.cambricon", 0, 0);


        //人体属性检测（26属性）输入192*256，description = ['Hat', 'Glasses', 'ShortSleeve', 'LongSleeve', 'UpperStride', 'UpperLogo', 'UpperPlaid', 'UpperSplice', 'LowerStripe',
        // 'LowerPattern', 'LongCoat', 'Trousers', 'Shorts', 'Skirt&Dress', 'boots', 'HandBag', 'ShoulderBag', 'Backpack', 'HoldObjectsInFront', 'AgeOver60', 'Age18-60',
        // 'AgeLess18', 'Female', 'Front', 'Side', 'Back']
        //long cnrt_body_attr_detect = face_tools.cnrtNetInit("strbs_intx_4_1.cambricon", 0, 0);
        long cnrt_body_attr_detect = face_tools.cnrtNetInit("strbs_intx_4_5.cambricon", 0, 0);


        CountDownLatch latch = new CountDownLatch(num);//初始化countDown
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            int start = end ;
            end = start + baseNum;
            if(i == (num-1)){
                end = length;
            }else if( i < remainderNum){
                end = end + 1;
            }

            BufferedImage b1 = null;
            try {
                b1 = ImageIO.read(new File(first_picture_path));
//                    bi = resize(img_name, picSize, picSize);
            } catch (Exception e) {
                e.printStackTrace();
            }

            byte[] rgb_1 = image2ByteArr(b1);

            //人脸口罩检测，创建session
            //long cnrt_facemaskdetect_sess = face_tools.forkSession(cnrt_facemaskdetect);

            //追踪，创建session和初始化
//            long cnrt_facedetect_sess = face_tools.forkSession(cnrt_facedetect);
//            long cnrt_facetrack_sess = face_tools.forkSession(cnrt_facetrack);
//            face_tools.faceTrackingInit(rgb_1, cnrt_facedetect_sess, b1.getWidth(), b1.getHeight(), 350, 2, rotation, picSize);

            //检测，创建session
//            long cnrt_facedetect_sess = face_tools.forkSession(cnrt_facedetect);

            //特征提取，创建session
//            long cnrt_facefeature_sess = face_tools.forkSession(cnrt_facefeature);

            //目标检测，创建session
            //long cnrt_yolov3_sess = face_tools.forkSession(cnrt_object_detect);

            //人体关键点检测yolov3+pose_model
            //long cnrt_yolov3_sess = face_tools.forkSession(cnrt_object_detect);
            //long cnrt_pose_sess = face_tools.forkSession(cnrt_pose_detect);

            //人体关键点检测追踪retina+pose_model
//            long cnrt_bodydetect_sess = face_tools.forkSession(cnrt_bodydetect);
//            long cnrt_bodytrack_sess = face_tools.forkSession(cnrt_bodytrack);
            //long cnrt_pose_sess = face_tools.forkSession(cnrt_pose_model);
            //face_tools.bodyTrackingInit(rgb_1, cnrt_bodydetect_sess, b1.getWidth(), b1.getHeight(), 350, 2, rotation);

            //人体属性检测cnrt_body_attr_detect
            long cnrt_body_attr_sess = face_tools.forkSession(cnrt_body_attr_detect);



            //口罩检测，创建session
            //Thread thread = new Test_multi_cnrt_demo(face_tools, cnrt_facemaskdetect_sess, i, latch, fileResult, rotation, picSize, start, end);
//            Thread thread = new Test_multi_cnrt_demo(face_tools, cnrt_facetrack_sess, cnrt_facedetect_sess, i, latch, fileResult, rotation, picSize, start, end);
            //目标检测，创建session
            //Thread thread = new Test_multi_cnrt_demo(face_tools, cnrt_yolov3_sess, i, latch, fileResult, rotation, picSize, start, end);
            //人体关键点检测yolov3+pose_model
            //Thread thread = new Test_multi_cnrt_demo(face_tools, cnrt_yolov3_sess, cnrt_pose_sess, i, latch, fileResult, rotation, picSize, start, end);
            //人体关键点检测追踪retina+pose_model
            //Thread thread = new Test_multi_cnrt_demo(face_tools, cnrt_bodydetect_sess, cnrt_bodytrack_sess, i, latch, fileResult, rotation, picSize, start, end);
            //Thread thread = new Test_multi_cnrt_demo(face_tools, cnrt_bodydetect_sess, cnrt_bodytrack_sess, cnrt_pose_sess, i, latch, fileResult, rotation, picSize, start, end);

            //人体属性检测
            Thread thread = new Test_multi_cnrt_demo(face_tools, cnrt_body_attr_sess, i, latch, fileResult, rotation, picSize, start, end);
            thread.start();
        }

        latch.await();//等待所有线程完成工作
//        net.release(); //所有线程完成后释放c++对象
        //人脸口罩识别
        //face_tools.cnrtNetRelease(cnrt_facemaskdetect);
        //追踪
//        face_tools.cnrtNetRelease(cnrt_facedetect);
//        face_tools.cnrtNetRelease(cnrt_facetrack);
        //检测特征提取
//        face_tools.cnrtNetRelease(cnrt_facefeature);
        //目标检测
        //face_tools.cnrtNetRelease(cnrt_object_detect);

        //人体关键点检测yolov3+pose_model
        //face_tools.cnrtNetRelease(cnrt_object_detect);
        //face_tools.cnrtNetRelease(cnrt_pose_detect);
        //人体关键点检测yolov3+pose_model
//        face_tools.cnrtNetRelease(cnrt_bodydetect);
//        face_tools.cnrtNetRelease(cnrt_bodytrack);
        //face_tools.cnrtNetRelease(cnrt_pose_model);

        //人体属性检测
        face_tools.cnrtNetRelease(cnrt_body_attr_detect);
        long endTime = System.currentTimeMillis();
        long t1 = endTime - startTime;
        System.out.println("总耗时:"+t1+"毫秒");
    }
}







