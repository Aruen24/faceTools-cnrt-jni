//package faceTools;
//
//
//import java.util.Vector;
//
//public class FaceTools_back {
//    static {
//        System.loadLibrary("FaceTools");
////        System.load("/home/wang/share/cnrtNetJNI/lib/libFaceTools.so");
//    }
//
//    private native long createSession(String model_path, int detType);
//    private native void releaseSession(long session);
//
//    private native long cnrtNetInit(String model_name, int dev_id, int dev_channel, long handle);
//    private native void cnrtNetRelease(long cnrt_net, long handle);
//
//    //检测追踪
//    private native int  faceTrackingInit(byte[] data, long detect_model_sess, int width, int height, int detTimeInterval, int trackTimeInterval, int rotation, int resize, long handle);
//    private native int trackingUpdate(byte[] data, long track_model_sess, long detect_model_sess, int width, int height, int rotation, int resize, long handle);
//    private native float[][] getTrackFaces(int rotation, long handle);
//
//    private native long cnrtGetSession(long cnrt_net, long handle);
//    private native long cnrtForkSession(long cnrt_net, long handle);
//
//    private native void cnrtSessionRelease(long sess, long handle);
//
//    //传入图片byte数组，不做resize,用于特征提取、分类
//    public native float[] cnrtForward(byte[] data, int width, int height, int rotation, int data_type, long sess, long handle);
//    //传入图片byte数组，并做resize，BGR->RGB，用于人脸检测
//    public native float[][] cnrtForwardResizeRgb(byte[] data, int width, int height, int rotation, int resize, int data_type, long sess, long handle);
//    //传入图片byte数组，并做resize、图片扩充，BGR，用于retinalface人脸检测
//    public native float[][] cnrtForwardExpandImgRgb(byte[] data, int width, int height, int rotation, int resize,int data_type, long sess, long handle);
//
//    private long handle;
//
//    public FaceTools_back(String model_path, int detType) {
//        handle = createSession(model_path, detType);
//    }
//
//    public void releaseFaceTools() {
//        releaseSession(handle);
//    }
//
//
//    /**
//     * 初始化不同的网络模型
//     * @param model_name
//     * @param dev_id
//     * @param dev_channel
//     * @return
//     */
//    public long cnrtNetInit(String model_name, int dev_id, int dev_channel){
//        return cnrtNetInit(model_name, dev_id, dev_channel, handle);
//    }
//
//    /**
//     * 对初始化创建的cnrtNet做release
//     * @param cnrt_net
//     */
//    public void cnrtNetRelease(long cnrt_net) {
//        cnrtNetRelease(cnrt_net, handle);
//    }
//
//
//    /**
//     * 人脸追踪初始化
//     * @param data
//     * @param detect_model_sess
//     * @param width
//     * @param height
//     * @param detTimeInterval
//     * @param trackTimeInterval
//     * @param rotation
//     * @param resize
//     * @return
//     */
//    public int  faceTrackingInit(byte[] data, long detect_model_sess, int width, int height, int detTimeInterval, int trackTimeInterval, int rotation, int resize){
//        return faceTrackingInit(data, detect_model_sess, width, height, detTimeInterval, trackTimeInterval, rotation, resize, handle);
//    }
//
//    /**
//     *传入新帧数据更新人脸框
//     * @param data
//     * @param width
//     * @param height
//     * @param rotation
//     * @param resize
//     * @return
//     */
//    public int trackingUpdate(byte[] data, long track_model_sess, long detect_model_sess, int width, int height, int rotation, int resize){
//        return trackingUpdate(data, track_model_sess, detect_model_sess, width, height, rotation, resize, handle);
//    }
//
//    /**
//     *获取人脸框
//     * @param rotation
//     * @return
//     */
//    public  Vector<Box> getTrackFaces(int rotation){
//        return toCnrtBox(getTrackFaces(rotation, handle));
//    }
//
//
//    /**
//     * 根据网络模型创建session，推理时候会用到
//     * @param cnrt_net
//     * @return
//     */
//    public long forkSession(long cnrt_net) {
//        return cnrtForkSession(cnrt_net, handle);
//    }
//
//    /**
//     * 对创建的cnrtSession做release
//     * @param cnrt_sess
//     */
//    public void cnrtSessionRelease(long cnrt_sess) {
//        cnrtSessionRelease(cnrt_sess, handle);
//    }
//
//    /**
//     * 模型推理，主要用于分类、特征提取
//     * @param data
//     * @param width
//     * @param height
//     * @param data_type
//     * @param sess
//     * @return
//     */
//    public float[] cnrtForward(byte[] data, int width, int height, int rotation, int data_type, long sess){
//        return cnrtForward(data, width, height, rotation, data_type, sess, handle);
//    }
//
//    /**
//     * 模型推理，主要用于人脸检测，输入图片只做resize
//     * @param data
//     * @param width
//     * @param height
//     * @param resize
//     * @param data_type
//     * @param sess
//     * @return
//     */
//    public float[][] cnrtForwardResizeRgb(byte[] data, int width, int height,int  rotation, int resize, int data_type, long sess){
//        return cnrtForwardResizeRgb(data, width, height, rotation, resize, data_type, sess, handle);
//    }
//
//    /**
//     * 模型推理，主要用于人脸检测
//     * 输入图片做扩充、做resize，输出原始二维数组
//     * @param data
//     * @param width
//     * @param height
//     * @param resize
//     * @param data_type
//     * @param sess
//     * @return
//     */
//    public float[][] cnrtForwardExpandImgRgb(byte[] data, int width, int height,int rotation, int resize, int data_type, long sess){
//        return cnrtForwardExpandImgRgb(data, width, height, rotation, resize, data_type, sess, handle);
//    }
//
//
//    /**
//     * retinaface人脸检测
//     * 对输入图片进行扩充和resize，输出人脸框结果
//     * @param data
//     * @param width
//     * @param height
//     * @param resize
//     * @param data_type
//     * @param new_sess
//     * @return
//     */
//    public Vector<Box> cnrtFaceDetectByRgb(byte[] data, int width, int height, int rotation, int resize, int data_type, long new_sess) {
//        return toCnrtBox(cnrtForwardExpandImgRgb(data, width, height, rotation, resize, data_type, new_sess));
//    }
//
//    /**
//     * 人脸特征提取
//     * @param data
//     * @param width
//     * @param height
//     * @param data_type
//     * @param new_sess
//     * @return
//     */
//    public float[] cnrtFaceFeatureByRgb(byte[] data, int width, int height, int rotation, int data_type, long new_sess) {
//        return cnrtForward(data, width, height, rotation, data_type, new_sess);
//    }
//
//    /**
//     * 物体检测
//     * @param data
//     * @param width
//     * @param height
//     * @param data_type
//     * @param new_sess
//     * @return
//     */
//    public float[] cnrtObjectDetectByRgb(byte[] data, int width, int height, int rotation, int data_type, long new_sess) {
//        return cnrtForward(data, width, height, rotation, data_type, new_sess);
//    }
//
//
//    public static Vector<Box> toCnrtBox(float[][] bbox) {
//        Vector<Box> boxes = new Vector<Box>();
//        for(int i=0; i<bbox.length; ++i) {
//            Box box = new Box();
//
//            box.box[0] = (int)bbox[i][0];
//            box.box[1] = (int)bbox[i][1];
//            box.box[2] = (int)bbox[i][2];
//            box.box[3] = (int)bbox[i][3];
//
//            for(int j=0; j<10; ++j) {
//                box.landmark[j] = bbox[i][j+4];
//            }
//
//            box.score = bbox[i][14];
//
//            box.calAngles();
//            boxes.add(box);
//        }
//
//        return boxes;
//    }
//
//    /**
//     * 人脸特征比对
//     * @param feature1
//     * @param feature2
//     * @return
//     */
//    public static float compare(float[] feature1, float[] feature2) {
//        if (feature1 == null || feature2 == null) {
//            return 0;
//        }
//        assert feature1.length != feature2.length;
//        float sum = 0.0f;
//        for (int i = 0; i < feature1.length; ++i) {
//            sum += (feature1[i] - feature2[i]) * (feature1[i] - feature2[i]);
//        }
//        double similar = (Math.cos(Math.min(sum*sum, 3.14)) + 1) * 0.5;
//
//        return (float) similar;
//    }
//
//}
