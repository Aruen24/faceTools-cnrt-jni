package faceTools;


import java.util.Vector;

public class FaceTools {
    static {
        System.loadLibrary("FaceTools");
//        System.load("/home/wang/share/cnrtNetJNI/lib/libFaceTools.so");
    }

    private native long createSession(String model_path, int detType);
    private native void releaseSession(long session);

    private native long cnrtNetInit(String model_name, int dev_id, int dev_channel, long handle);
    private native void cnrtNetRelease(long cnrt_net, long handle);

    //人脸检测追踪
    private native int  faceTrackingInit(byte[] data, long detect_model_sess, int width, int height, int detTimeInterval, int trackTimeInterval, int rotation, int resize, long handle);
    private native int trackingUpdate(byte[] data, long track_model_sess, long detect_model_sess, int width, int height, int rotation, int resize, long handle);
    private native float[][] getTrackFaces(int rotation, long handle);

    //人体检测追踪
    private native int bodyTrackingInit(byte[] data, long detect_model_sess_, int width, int height, int detTimeInterval, int trackTimeInterval, int rotation, long handle);
    private native int bodyTrackingUpdate(byte[] data, long track_model_sess_, long detect_model_sess_, int width, int height, int rotation, long handle);
    private native float[][] getTrackBodys(int rotation, long handle);

    private native long cnrtGetSession(long cnrt_net, long handle);
    private native long cnrtForkSession(long cnrt_net, long handle);

    private native void cnrtSessionRelease(long sess, long handle);

    //人脸口罩识别，传入截取后人脸框图片byte, 网络输入112*112
    public native long cnrtMaskDetectForwardRect(byte[] data, int width, int height, int rotation, int resize, int data_type, long sess, long handle);
    //人脸口罩识别，传入原图byte数组，人脸框位置， 网络输入112*112
    public native long cnrtMaskDetectForward(byte[] data, int width, int height, int pos_x, int pos_y, int pos_w, int pos_h, int rotation, int resize, int data_type, long sess, long handle);
    //传入图片byte数组，不做resize,用于特征提取、分类
    public native float[] cnrtForward(byte[] data, int width, int height, int rotation, int data_type, long sess, long handle);
    //传入截取后人脸框图片byte数组，用于特征提取 type_mask 0-mask 1-nomask
    public native float[] cnrtForwardRectByFeature(byte[] data, int width, int height, int rotation, int resize, int data_type, int type_mask, long sess, long handle);
    //传入原图byte数组，人脸框位置，用于特征提取 type_mask 0-mask 1-nomask
    public native float[] cnrtForwardByFeature(byte[] data, int width, int height, int pos_x, int pos_y, int pos_w, int pos_h, int rotation, int resize, int data_type, int type_mask, long sess, long handle);
    //传入图片byte数组，并做resize，BGR->RGB，用于人脸检测
    public native float[][] cnrtForwardResizeRgb(byte[] data, int width, int height, int rotation, int resize, int data_type, long sess, long handle);
    //传入图片byte数组，并做resize、图片扩充，BGR，用于retinalface人脸检测
    public native float[][] cnrtForwardExpandImgRgb(byte[] data, int width, int height, int rotation, int resize,int data_type, long sess, long handle);
    //传入图片byte数组,用于目标检测 string classNames[10] =  {"person", "bicycle", "car", "motorbike", "aeroplane", "bus", "train",  "truck"}
    public native float[][] cnrtForwardByYoloV3(byte[] data, int width, int height, int rotation, int resize, int data_type, long sess, long handle);
    //传入图片byte数组,用于人体关键点检测（Yolov3/Retinaface+Alphapose合在一起）
    public native float[][] cnrtForwardByYoloV3AlphaPose(byte[] data, int width, int height, int rotation, int resize, int data_type, long body_yolo_sess_, long land_pose_sess_, long handle);
    //传入原图片和人体坐标框,用于人体关键点检测（单独的Alphapose）
    public native float[][] cnrtForwardByAlphaPose(byte[] data, int width, int height, int rotation, long land_pose_sess_, float[][] arrRect, long handle);

    public native float[][] cnstreamTrackBodys(byte[] data, long detect_model_sess_, int width, int height, int rotation, long handle);
    //人体属性检测（26属性）网络输入192*256，传入小图人体图片
    public native float[][] cnrtBodyAttrForwardRect(byte[] data, int width, int height, int rotation, int resize_w, int resize_h, int data_type, long sess_, long handle);
    //人体属性检测（26属性）网络输入192*256，传入大图以及小图的人体框坐标
    public native float[][] cnrtBodyAttrForward(byte[] data, int width, int height, int pos_x, int pos_y, int pos_w, int pos_h, int rotation, int resize_w, int resize_h, int data_type, long sess_, long handle);
    //人体属性检测（26属性）,batch=5、批量推理的方法，传入的是人体检测出来人体框坐标的数组
    public native float[][] cnrtBodyAttrForwardResults(byte[] data, int width, int height, int rotation, int resize_w, int resize_h, int data_type, long sess_, float[][] arrRect, long handle);

    //行为检测，网络输入128*128骨架图数据  0-fall   1-normal
    public native long cnrtActionDetectForward(byte[] data, int width, int height, int data_type, long sess, long handle);
    //传入图片byte数组,用于目标检测
    public native float[][] cnrtForwardByYoloV5(byte[] data, int width, int height, int rotation, int data_type, long sess, long handle);

    private long handle;

    public FaceTools(String model_path, int detType) {
        handle = createSession(model_path, detType);
    }

    public void releaseFaceTools() {
        releaseSession(handle);
    }


    /**
     * 初始化不同的网络模型
     * @param model_name
     * @param dev_id
     * @param dev_channel
     * @return
     */
    public long cnrtNetInit(String model_name, int dev_id, int dev_channel){
        return cnrtNetInit(model_name, dev_id, dev_channel, handle);
    }

    /**
     * 对初始化创建的cnrtNet做release
     * @param cnrt_net
     */
    public void cnrtNetRelease(long cnrt_net) {
        cnrtNetRelease(cnrt_net, handle);
    }


    /**
     * 人脸追踪初始化
     * @param data
     * @param detect_model_sess
     * @param width
     * @param height
     * @param detTimeInterval
     * @param trackTimeInterval
     * @param rotation
     * @param resize
     * @return
     */
    public int  faceTrackingInit(byte[] data, long detect_model_sess, int width, int height, int detTimeInterval, int trackTimeInterval, int rotation, int resize){
        return faceTrackingInit(data, detect_model_sess, width, height, detTimeInterval, trackTimeInterval, rotation, resize, handle);
    }

    /**
     *传入新帧数据更新人脸框
     * @param data
     * @param width
     * @param height
     * @param rotation
     * @param resize
     * @return
     */
    public int trackingUpdate(byte[] data, long track_model_sess, long detect_model_sess, int width, int height, int rotation, int resize){
        return trackingUpdate(data, track_model_sess, detect_model_sess, width, height, rotation, resize, handle);
    }

    /**
     *获取人脸框
     * @param rotation
     * @return
     */
    public  Vector<Box> getTrackFaces(int rotation){
        return toCnrtBox(getTrackFaces(rotation, handle));
    }


    /**
     * 人体追踪初始化
     * @param data
     * @param detect_model_sess
     * @param width
     * @param height
     * @param detTimeInterval
     * @param trackTimeInterval
     * @param rotation
     * @return
     */
    public int  bodyTrackingInit(byte[] data, long detect_model_sess, int width, int height, int detTimeInterval, int trackTimeInterval, int rotation){
        return bodyTrackingInit(data, detect_model_sess, width, height, detTimeInterval, trackTimeInterval, rotation, handle);
    }

    /**
     *传入新帧数据更新body框
     * @param data
     * @param width
     * @param height
     * @param rotation
     * @return
     */
    public int  bodyTrackingUpdate(byte[] data, long track_model_sess_, long detect_model_sess_, int width, int height, int rotation){
        return bodyTrackingUpdate(data, track_model_sess_, detect_model_sess_, width, height, rotation, handle);
    }

    /**
     *获取body框
     * @param rotation
     * @return
     */
    public  float[][] getTrackBodys(int rotation){
        return getTrackBodys(rotation, handle);
    }


    /**
     * 根据网络模型创建session，推理时候会用到
     * @param cnrt_net
     * @return
     */
    public long forkSession(long cnrt_net) {
        return cnrtForkSession(cnrt_net, handle);
    }

    /**
     * 对创建的cnrtSession做release
     * @param cnrt_sess
     */
    public void cnrtSessionRelease(long cnrt_sess) {
        cnrtSessionRelease(cnrt_sess, handle);
    }

    /**
     * 模型推理，主要用于分类、特征提取
     * @param data
     * @param width
     * @param height
     * @param data_type
     * @param sess
     * @return
     */
    public float[] cnrtForward(byte[] data, int width, int height, int rotation, int data_type, long sess){
        return cnrtForward(data, width, height, rotation, data_type, sess, handle);
    }

    /**
     * 模型推理，人脸口罩识别，输入截取后人脸、网络输入112*112
     * @param data
     * @param width
     * @param height
     * @param data_type
     * @param sess
     * @return
     */
    public long cnrtMaskDetectForwardRect(byte[] data, int width, int height, int rotation, int resize, int data_type, long sess){
        return cnrtMaskDetectForwardRect(data, width, height, rotation, resize, data_type, sess, handle);
    }

    /**
     * 模型推理，人脸口罩识别，输入截取后人脸、网络输入112*112
     * @param data
     * @param width
     * @param height
     * @param data_type
     * @param sess
     * @return
     */
    public long cnrtMaskDetectForward(byte[] data, int width, int height,  int pos_x, int pos_y, int pos_w, int pos_h, int rotation, int resize, int data_type, long sess){
        return cnrtMaskDetectForward(data, width, height, pos_x, pos_y, pos_w, pos_h, rotation, resize, data_type, sess, handle);
    }

    /**
     * 模型推理，主要用于特征提取,输入原图、人脸框位置
     * @param data
     * @param width
     * @param height
     * @param pos_x
     * @param pos_y
     * @param pos_w
     * @param pos_h
     * @param rotation
     * @param data_type
     * @param sess
     * @return
     */
    public float[] cnrtForwardByFeature(byte[] data, int width, int height, int pos_x, int pos_y, int pos_w, int pos_h, int rotation, int resize, int data_type, int type_mask, long sess){
        return cnrtForwardByFeature(data, width, height, pos_x, pos_y, pos_w, pos_h, rotation, resize, data_type, type_mask, sess, handle);
    }

    /**
     * 模型推理，主要用于特征提取，输入截取后人脸框位置
     * @param data
     * @param width
     * @param height
     * @param resize
     * @param rotation
     * @param data_type
     * @param sess
     * @return
     */
    public float[] cnrtForwardByRectFeature(byte[] data, int width, int height, int rotation, int resize, int data_type, int type_mask, long sess){
        return cnrtForwardRectByFeature(data, width, height, rotation, resize, data_type, type_mask, sess, handle);
    }

    /**
     * 模型推理，主要用于人脸检测，输入图片只做resize
     * @param data
     * @param width
     * @param height
     * @param resize
     * @param data_type
     * @param sess
     * @return
     */
    public float[][] cnrtForwardResizeRgb(byte[] data, int width, int height,int  rotation, int resize, int data_type, long sess){
        return cnrtForwardResizeRgb(data, width, height, rotation, resize, data_type, sess, handle);
    }

    /**
     * 模型推理，主要用于人脸检测
     * 输入图片做扩充、做resize，输出原始二维数组
     * @param data
     * @param width
     * @param height
     * @param resize
     * @param data_type
     * @param sess
     * @return
     */
    public float[][] cnrtForwardExpandImgRgb(byte[] data, int width, int height,int rotation, int resize, int data_type, long sess){
        return cnrtForwardExpandImgRgb(data, width, height, rotation, resize, data_type, sess, handle);
    }


    /**
     * retinaface人脸检测
     * 对输入图片进行扩充和resize，输出人脸框结果
     * @param data
     * @param width
     * @param height
     * @param resize
     * @param data_type
     * @param new_sess
     * @return
     */
    public Vector<Box> cnrtFaceDetectByRgb(byte[] data, int width, int height, int rotation, int resize, int data_type, long new_sess) {
        return toCnrtBox(cnrtForwardExpandImgRgb(data, width, height, rotation, resize, data_type, new_sess));
    }


    /**
     * 物体检测
     * @param data
     * @param width
     * @param height
     * @param data_type
     * @param new_sess
     * @return
     */
    public float[][] cnrtObjectDetectByYoloV3Rgb(byte[] data, int width, int height, int rotation, int resize, int data_type, long new_sess) {
        return cnrtForwardByYoloV3(data, width, height, rotation, resize, data_type, new_sess, handle);
    }

    /**
     * 人体关键点检测（Yolov3/Retinaface+Alphapose）
     * @param data
     * @param width
     * @param height
     * @param rotation
     * @param resize
     * @param data_type
     * @param body_yolo_sess
     * @param land_pose_sess
     * @return
     */
    public float[][] cnrtForwardByYoloV3AlphaPose(byte[] data, int width, int height, int rotation, int resize, int data_type, long body_yolo_sess, long land_pose_sess) {
        return cnrtForwardByYoloV3AlphaPose(data, width, height, rotation, resize, data_type, body_yolo_sess, land_pose_sess, handle);
    }

    /**
     * 单独的人体关键点检测（Alphapose）
     * @param data
     * @param width
     * @param height
     * @param rotation
     * @param land_pose_sess
     * @return
     */
    public float[][] cnrtForwardByAlphaPose(byte[] data, int width, int height, int rotation, long land_pose_sess, float[][] arrRect) {
        return cnrtForwardByAlphaPose(data, width, height, rotation, land_pose_sess, arrRect, handle);
    }

    /**
     *
     * @param data
     * @param width
     * @param height
     * @param rotation
     * @param detect_model_sess_
     * @return
     */
    public float[][] cnstreamTrackBodys(byte[] data, long detect_model_sess_, int width, int height, int rotation) {
        return cnstreamTrackBodys(data, detect_model_sess_, width, height, rotation, handle);
    }

    /**
     *
     * @param data
     * @param width
     * @param height
     * @param rotation
     * @param resize_w
     * @param resize_h
     * @param data_type
     * @param sess_
     * @return
     */
    public float[][] cnrtBodyAttrForwardRectRgb(byte[] data, int width, int height, int rotation, int resize_w, int resize_h, int data_type, long sess_) {
        return cnrtBodyAttrForwardRect(data, width, height, rotation, resize_w, resize_h, data_type, sess_, handle);
    }

    /**
     *
     * @param data
     * @param width
     * @param height
     * @param pos_x
     * @param pos_y
     * @param pos_w
     * @param pos_h
     * @param rotation
     * @param resize_w
     * @param resize_h
     * @param data_type
     * @param sess_
     * @return
     */
    public float[][] cnrtBodyAttrForwardRgb(byte[] data, int width, int height, int pos_x, int pos_y, int pos_w, int pos_h, int rotation, int resize_w, int resize_h, int data_type, long sess_) {
        return cnrtBodyAttrForward(data, width, height, pos_x, pos_y, pos_w, pos_h, rotation, resize_w, resize_h, data_type, sess_, handle);
    }

    /**
     * @param data
     * @param width
     * @param height
     * @param rotation
     * @param sess_
     * @return
     */
    public float[][] cnrtBodyAttrForwardResultsRgb(byte[] data, int width, int height, int rotation, int resize_w, int resize_h, int data_type, long sess_, float[][] arrRect) {
        return cnrtBodyAttrForwardResults(data, width, height, rotation,  resize_w, resize_h, data_type, sess_, arrRect, handle);
    }

    /**
     *
     * @param data
     * @param width
     * @param height
     * @param data_type
     * @param sess
     * @return
     */
    public long cnrtActionDetectForwardRgb(byte[] data, int width, int height, int data_type, long sess){
        return cnrtActionDetectForward(data, width, height, data_type, sess, handle);
    }


    /**
     * 物体检测
     * @param data
     * @param width
     * @param height
     * @param data_type
     * @param new_sess
     * @return
     */
    public float[][] cnrtObjectDetectByYoloV5Rgb(byte[] data, int width, int height, int rotation, int data_type, long new_sess) {
        return cnrtForwardByYoloV5(data, width, height, rotation, data_type, new_sess, handle);
    }


    public static Vector<Box> toCnrtBox(float[][] bbox) {
        Vector<Box> boxes = new Vector<Box>();
        for(int i=0; i<bbox.length; ++i) {
            Box box = new Box();

            box.box[0] = (int)bbox[i][0];
            box.box[1] = (int)bbox[i][1];
            box.box[2] = (int)bbox[i][2];
            box.box[3] = (int)bbox[i][3];

            for(int j=0; j<10; ++j) {
                box.landmark[j] = bbox[i][j+4];
            }

            if(bbox[i].length == 15) {
                box.score = bbox[i][14];
            }

            if(bbox[i].length == 16) {
                box.track_id = (int)bbox[i][14];
                box.score = bbox[i][15];
            }

            box.calAngles();
            boxes.add(box);
        }

        return boxes;
    }

    /**
     * 人脸特征比对
     * @param feature1
     * @param feature2
     * @return
     */
    public static float compare(float[] feature1, float[] feature2) {
        if (feature1 == null || feature2 == null) {
            return 0;
        }
        assert feature1.length != feature2.length;
        float sum = 0.0f;
        for (int i = 0; i < feature1.length; ++i) {
            sum += (feature1[i] - feature2[i]) * (feature1[i] - feature2[i]);
        }
        double similar = (Math.cos(Math.min(sum*sum, 3.14)) + 1) * 0.5;

        return (float) similar;
    }

}
