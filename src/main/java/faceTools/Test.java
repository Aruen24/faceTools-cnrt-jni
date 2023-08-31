//package faceTools;//
////package faceTools;
//
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.util.List;
//import java.util.Vector;
//
//
//public class Test {
//    /**
//     * 图片转成RGB byte数组
//     * @param img
//     * @return
//     * @auther Aruen
//     * @date 2020.10.29
//     */
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
//     * 图片在多级目录下处理
//     * @param picPath
//     * @return
//     * @auther Aruen
//     * @date 2020.10.30
//     */
//    public static List<String> multifile(String picPath){
//        List<String> files = new ArrayList<String>();
//        File file = new File(picPath);
//        if(file!=null){// 判断对象是否为空
//            if(!file.isFile()){
//                File[] tempList = file.listFiles() ;// 列出全部的文件
//                for(int i=0;i<tempList.length;i++){
//                    if(tempList[i].isDirectory()){// 如果是目录，二级目录
//                        File[] p_path = tempList[i].listFiles() ;// 列出全部的文件
//                        for(int j=0;j<p_path.length;j++){
//                            if(p_path[j].isDirectory()){// 如果是目录,三级目录
//                                File[] p_path_result = p_path[j].listFiles() ;// 列出全部的文件
//                                for(int k=0;k<p_path_result.length;k++){
//                                    files.add(p_path_result[k].toString());
//                                }
//
//                            }else if(p_path[j].isFile()){
//                                files.add(p_path[j].toString());// 如果不是目录，输出路径
//                            }
//                        }
//                    }else if(tempList[i].isFile()){
//                        files.add(tempList[i].toString());// 如果不是目录，输出路径
//                    }
//                }
//            }else{
//                files.add(file.toString());// 如果不是目录，输出路径
//            }
//            return files;
//        }else{
//            return null;
//        }
//    }
//
//    public static Vector<Box> detectExpandFacesByRgb(float[][] result, int width, int height) {
//
//        Vector<Box> boxes = new Vector<Box>();
//        for(int k=0; k<result.length; ++k) {
//            Box box = new Box();
//            box.box[0] = (int)result[k][0];
//            box.box[1] = (int)result[k][1];
//            box.box[2] = (int)result[k][2];
//            box.box[3] = (int)result[k][3];
//
//            for(int j=0; j<10; ++j) {
//                box.landmark[j] = result[k][j+6];
//            }
//
//            box.score = result[k][5];
//
//            box.calAngles();
//            box.limit_square(width, height);
//            boxes.add(box);
//        }
//
//        return boxes;
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
//    public static void getResultByRgb(String modelPath, int picSize, List<String> fileResult, FaceTools face_tools, int rotation){
//        int count = 0;
//
//
//        //追踪，创建加载模型
//        long cnrt_facedetect = face_tools.cnrtNetInit("retinaface_mobile0.25_intx_1_1_int8.cambricon", 0, 0);
////        long cnrt_facedetect = face_tools.cnrtNetInit("retinaface_resnet50_intx_1_1_int16.cambricon", 0, 0);
////        long cnrt_facetrack = face_tools.cnrtNetInit("onet_int8_b1_c1.cambricon", 0, 0);
//        long cnrt_facetrack = face_tools.cnrtNetInit("retinaface_mobile0.25_intx_1_1_int8.cambricon", 0, 0);
////        long cnrt_facetrack = face_tools.cnrtNetInit("retinaface_resnet50_intx_1_1_int16.cambricon", 0, 0);
//
//        //检测，创建加载模型
////        long cnrt_facedetect = face_tools.cnrtNetInit("retinaface_mobile0.25_intx_1_1_int8.cambricon", 0, 0);
//
//        //提取特征，创建加载模型 resnet101_int16_b1_c1.cambricon
////        long cnrt_facefeature = face_tools.cnrtNetInit("vargfacenet_int8_b1_c1.cambricon", 0, 0);
//
//
//
//        BufferedImage b1 = null;
//        try {
//            b1 = ImageIO.read(new File("./600_800.jpg"));
////                    bi = resize(img_name, picSize, picSize);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        byte[] rgb_1 = image2ByteArr(b1);
//
//        //追踪，创建session和初始化
//        long cnrt_facedetect_sess = face_tools.forkSession(cnrt_facedetect);
//        long cnrt_facetrack_sess = face_tools.forkSession(cnrt_facetrack);
//        face_tools.faceTrackingInit(rgb_1, cnrt_facedetect_sess, b1.getWidth(), b1.getHeight(), 350, 2, rotation, picSize);
//
//        //检测，创建session
////            long cnrt_facedetect_sess = face_tools.forkSession(cnrt_facedetect);
//
//        //特征提取，创建session
////            long cnrt_facefeature_sess = face_tools.forkSession(cnrt_facefeature);
//
//        for(String img_name : fileResult) {
//            BufferedImage bi = null;
//
//            try {
//                bi = ImageIO.read(new File(img_name));
////                bi = resize(img_name, picSize, picSize);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            byte[] rgb = image2ByteArr(bi);
//            long startTime = System.currentTimeMillis();
//
//            //追踪，更新和获取人脸
//            face_tools.trackingUpdate(rgb, cnrt_facetrack_sess, cnrt_facedetect_sess, bi.getWidth(), bi.getHeight(), rotation, picSize);
//            Vector<Box> boxes = face_tools.getTrackFaces(rotation);
//
//            //检测，推理获得结果
////                Vector<Box> boxes = face_tools.cnrtFaceDetectByRgb(rgb, bi.getWidth(), bi.getHeight(), rotation, picSize, 0, cnrt_facedetect_sess);
//
//            //特征提取，推理获得结果
////                float[] boxes = face_tools.cnrtFaceFeatureByRgb(rgb, bi.getWidth(), bi.getHeight(), rotation, 0, cnrt_facefeature_sess);
////                for (int i = 0; i < boxes.length; i++) {
////                    System.out.println(boxes[i]);
////                }
//
//            long endTime = System.currentTimeMillis();
//
//
//            for (int i = 0; i < boxes.size(); i++) {
//                int[] box = boxes.get(i).box;
//                float score_value = boxes.get(i).score;
//                float[] angle_value = boxes.get(i).angles;
//                System.out.println("left:"+box[0]+","+"top:"+box[1]+","+"right:"+box[2]+","+"bottom:"+box[3]);
//            }
//
//
//            count++;
//            System.out.println("模型计算耗时："+(endTime-startTime)+"ms******"+"处理图片数："+count+"----图片路径："+img_name);
//        }
//        face_tools.cnrtSessionRelease(cnrt_facedetect_sess);
//        face_tools.cnrtSessionRelease(cnrt_facetrack_sess);
//    }
//
//
//
//
//    public static void main(String args[]) {
//        String modelPath = args[0];
//        String picPath = args[1];
//        int picSize = Integer.parseInt(args[2]);
//        int rotation = 1;
//
//        //创建推理对象
//        FaceTools face_tools = new FaceTools(modelPath, 0);
//
//        List<String> fileResult = multifile(picPath);
//        if(fileResult != null) {
//            //得到结果，根据传入图片的byte数组，图片resize在java端处理
//            getResultByRgb(modelPath, picSize, fileResult, face_tools, rotation);
//        }
//
//    }
//}
//
