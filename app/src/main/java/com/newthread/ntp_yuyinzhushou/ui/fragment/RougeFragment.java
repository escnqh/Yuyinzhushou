package com.newthread.ntp_yuyinzhushou.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.autonavi.tbt.NaviStaticInfo;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.newthread.ntp_yuyinzhushou.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 启航 on 2016/10/26 0026.
 */

public class RougeFragment extends BaseFragment implements AMapNaviListener {
    private AMap mAMap;
    private View view;
    private AMapLocation currentLocation;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationClientOption;
    private Bundle bundle = getArguments();

    private NaviLatLng mNaviStart = null;
    private NaviLatLng mNaviEnd = null;

    // 起点终点列表
    private ArrayList<NaviLatLng> mStartPoints = new ArrayList<NaviLatLng>();
    private ArrayList<NaviLatLng> mEndPoints = new ArrayList<NaviLatLng>();

    // 规划线路
    private RouteOverLay mRouteOverLay;
    private AMapNavi aMapNavi;


    @BindView(R.id.map_view)
    MapView mMapView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.rougeview, container, false);
        ButterKnife.bind(this,view);
        aMapNavi.addAMapNaviListener(this);
        aMapNavi.setEmulatorNaviSpeed(150);
        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();
        mRouteOverLay = new RouteOverLay(mAMap, null,getContext());
        addRoute();
        return view;
    }

    /**
     * 获取当前定位
     */
    private void getLocation() {
        locationClient = new AMapLocationClient(getContext());
        locationClient.setLocationOption(locationClientOption);
        locationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                currentLocation = aMapLocation;
                changeMapCenter(currentLocation.getLatitude(), currentLocation.getLongitude(), true);
            }
        });
        locationClient.startLocation();
    }

    /**
     * 改变地图中心位置
     *
     * @param latitude     纬度
     * @param longitude    经度
     * @param isChangeZoom 是否改变缩放程度
     */
    private void changeMapCenter(double latitude, double longitude, boolean isChangeZoom) {
        float zoom = mAMap.getCameraPosition().zoom;
        CameraUpdate cameraUpdate;
        if (isChangeZoom) {
            cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(
                    new LatLng(latitude, longitude),//新的中心点坐标
                    16, // 新的缩放级别
                    0, //俯仰角0°~45°（垂直与地图时为0）
                    0  ////偏航角 0~360° (正北方为0)
            ));
        } else {
            cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(
                    new LatLng(latitude, longitude),//新的中心点坐标
                    zoom, // 新的缩放级别
                    0, //俯仰角0°~45°（垂直与地图时为0）
                    0  ////偏航角 0~360° (正北方为0)
            ));
        }
        mAMap.animateCamera(cameraUpdate);
    }

    /**
     * 改变格式
     */
    private NaviLatLng parseEditText(String text) {
        try {
            double latD = Double.parseDouble(text.split(",")[0]);
            double lonD = Double.parseDouble(text.split(",")[1]);
            return new NaviLatLng(latD, lonD);

        } catch (Exception e) {
            Toast.makeText(getContext(), "e:" + e, Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), "格式:[lat],[lon]", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    /**
     * 规划路线
     */
    private void addRoute() {
        double endLatitude=bundle.getDouble("elatitude");
        double endLongitude=bundle.getDouble("elongitude");
        mNaviStart = parseEditText(currentLocation.getLatitude()+","+currentLocation.getLongitude());
        mNaviEnd = parseEditText(endLatitude+","+endLongitude);
        boolean isSuccess = aMapNavi.calculateWalkRoute(mNaviStart, mNaviEnd);
        if (!isSuccess) {
            Toast.makeText(getContext(), "路线计算失败！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCalculateRouteFailure(int arg0) {

        Toast.makeText(getContext(),"路径规划出错" + arg0,Toast.LENGTH_SHORT).show();

    }

    /**
     * 获取路径规划线路，显示到地图上
     */
    @Override
    public void onCalculateRouteSuccess() {
        AMapNaviPath naviPath = aMapNavi.getNaviPath();
        if (naviPath == null) {
            return;
        }
        mRouteOverLay.setAMapNaviPath(naviPath);
        mRouteOverLay.addToMap();
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onInitNaviSuccess() {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onGetNavigationText(int i, String s) {

    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onArriveDestination() {

    }

    @Override
    public void onArriveDestination(NaviStaticInfo naviStaticInfo) {

    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onGpsOpenStatus(boolean b) {

    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

    }

    @Override
    public void hideLaneInfo() {

    }

    @Override
    public void onCalculateMultipleRoutesSuccess(int[] ints) {

    }

    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        mStartPoints.add(mNaviStart);
        mEndPoints.add(mNaviEnd);

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        aMapNavi.destroy();
    }

}
