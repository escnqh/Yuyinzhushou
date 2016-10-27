package com.newthread.ntp_yuyinzhushou.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MoChan on 2016/10/17.
 * LocationFragment内对关键词列表进行细节化的解析
 */

public class LocationDetailAdapter {

    private List<String> wordList;
    private LocationDetailAdapterImpl locationDetailAdapterImpl;
    private Map<String, String> typeMap;

    /**
     * 高德地图提供的搜索类型
     */
    private static final String[] poiTypes = {
            "汽车服务", "汽车销售", "汽车维修", "摩托车服务", "餐饮服务",
            "购物服务", "生活服务", "体育休闲服务", "医疗保健服务", "住宿服务",
            "风景名胜", "商务住宅", "政府机构及社会团体", "科教文化服务", "交通设施服务",
            "金融保险服务", "公司企业", "道路附属设施", "地名地址信息", "公共设施"
    };

    private static final String[] goToWords = {
            "去", "到"
    };

    private static final String[] nearbyWords = {
            "附近", "周边", "周围"
    };

    /**
     * 餐饮服务关键词
     */
    private String[] cateringServiceWords = {
            "饿", "吃", "喝", "渴", "口渴", "吃饭", "喝水",
            "餐厅", "餐馆", "饭店", "饭馆", "馆子",
            "果汁", "奶茶", "咖啡"
    };

    /**
     * 购物服务关键词
     */
    private String[] shopServiceWords = {
            "买", "购物", "买菜", "逛街",
            "菜市场", "市场", "超市", "商场", "百货", "步行街"
    };

    /**
     * 生活服务关键词
     */
    private String[] lifeServiceWords = {
            "邮", "寄", "邮寄",
            "桑拿", "足浴", "足疗", "按摩", "汗蒸",
            "美容", "美发", "理发", "剪发", "剪头", "洗头", "剃头", "洗发",
            "邮局", "邮政", "快递", "圆通", "申通", "韵达", "顺丰", "菜鸟", "驿站",
            "理发店", "美容店", "美发店"
    };

    /**
     * 体育休闲服务关键词
     */
    private String[] sportServiceWords = {
            "运动", "健身", "打球", "踢球", "游泳", "跑步", "散步", "骑车", "骑行",
            "唱歌", "跳舞", "喝酒", "上网",
            "足球", "篮球", "乒乓球", "羽毛球", "网球", "台球", "桌球",
            "运动场", "体育场", "体育馆", "运动馆", "足球场", "田径场", "游泳馆", "游泳场", "游泳池",
            "台球馆", "桌球馆", "羽毛球场", "羽毛球馆", "篮球场", "网球场", "乒乓球场", "乒乓球馆", "乒乓球台", "乒乓球室",
            "健身房", "歌厅", "KTV", "舞厅", "迪厅", "迪吧", "歌舞厅", "酒吧", "清吧",
            "网吧", "网咖"
    };

    /**
     * 医疗保健服务关键词
     */
    private String[] medicalServiceWords = {
            "病", "看病", "药", "吃药", "买药",
            "感冒", "发烧", "发热", "头疼", "头痛", "发炎", "骨折", "疼", "胃痛", "受伤", "中暑", "痛",
            "药店", "药房", "医院", "诊所", "门诊", "医务室", "医务所"
    };

    /**
     * 住宿服务关键词
     */
    private String[] hotelServiceWords = {
            "住宿", "住",
            "酒店", "宾馆", "旅馆", "旅店", "客栈", "钟点房", "客栈", "招待所"
    };

    /**
     * 风景名胜关键词
     */
    private String[] travelServiceWords = {
            "参观", "游玩", "游览",
            "公园", "博物馆", "名胜", "古迹", "风景", "动物园", "景点"
    };

    /**
     * 交通设施服务关键词
     */
    private String[] trafficServiceWords = {
            "停车", "公交站", "停车场"
    };

    /**
     * 金融保险服务关键词
     */
    private String[] bankServiceWords = {
            "存钱", "取钱", "借钱", "借贷", "贷款",
            "银行", "ATM", "取款机"
    };

    /**
     * 公共设施关键词
     */
    private String[] commonalityServiceWords = {
            "入厕", "如厕", "大号", "小号", "大解", "小解",
            "厕所", "公厕", "洗手间"
    };

    public LocationDetailAdapter(LocationDetailAdapterImpl locationDetailAdapterImpl, List<String> wordList) {
        this.locationDetailAdapterImpl = locationDetailAdapterImpl;
        this.wordList = wordList;
        initTypeMap();
        analyzeWords();
    }

    /**
     * 对键值对Map进行初始化
     */
    private void initTypeMap() {
        typeMap = new HashMap<>();
        //餐饮服务
        for (String cateringServiceWord : cateringServiceWords) {
            typeMap.put(cateringServiceWord, poiTypes[4]);
        }
        //购物服务
        for (String shopServiceWord : shopServiceWords) {
            typeMap.put(shopServiceWord, poiTypes[5]);
        }
        //生活服务
        for (String lifeServiceWord : lifeServiceWords) {
            typeMap.put(lifeServiceWord, poiTypes[6]);
        }
        //体育休闲服务
        for (String sportServiceWord : sportServiceWords) {
            typeMap.put(sportServiceWord, poiTypes[7]);
        }
        //医疗保健服务
        for (String medicalServiceWord : medicalServiceWords) {
            typeMap.put(medicalServiceWord, poiTypes[8]);
        }
        //住宿服务
        for (String hotelServiceWord : hotelServiceWords) {
            typeMap.put(hotelServiceWord, poiTypes[9]);
        }
        //风景名胜
        for (String travelServiceWord : travelServiceWords) {
            typeMap.put(travelServiceWord, poiTypes[10]);
        }
        //交通设施服务
        for (String trafficServiceWord : trafficServiceWords) {
            typeMap.put(trafficServiceWord, poiTypes[14]);
        }
        //金融保险服务
        for (String bankServiceWord : bankServiceWords) {
            typeMap.put(bankServiceWord, poiTypes[15]);
        }
        //公共设施服务
        for (String commonalityServiceWord : commonalityServiceWords) {
            typeMap.put(commonalityServiceWord, poiTypes[19]);
        }
    }

    /**
     * 对分词列表进行解析
     */
    private void analyzeWords() {
        String matchedType = null;
        if (isSpecialCondition(wordList)) {
            matchedType = chooseSpecialConditionType(wordList);
        } else {
            for (int i = 0; i < wordList.size(); i++) {
                matchedType = typeMap.get(wordList.get(i));
                if (matchedType != null) {
                    break;
                }
            }
        }
        if (isContainsNearbyWords(wordList)) {
            if (matchedType != null) {
                selectMatchedType(matchedType);
            } else {
                analyzeNearbyWords();
            }
        } else if (isContainsGoToWords(wordList)) {
            analyzeGoToWords();
        } else {
            if (matchedType != null) {
                selectMatchedType(matchedType);
            } else {
                analyzeUnknownWords();
            }
        }
    }

    private void selectMatchedType(String matchedType) {
        switch (matchedType) {
            case "汽车服务":
                break;
            case "汽车销售":
                break;
            case "汽车维修":
                break;
            case "摩托车服务":
                break;
            case "餐饮服务":
                analyzeCateringServiceWords();
                break;
            case "购物服务":
                analyzeShopServiceWords();
                break;
            case "生活服务":
                analyzeLifeServiceWords();
                break;
            case "体育休闲服务":
                analyzeSportServiceWords();
                break;
            case "医疗保健服务":
                analyzeMedicalServiceWords();
                break;
            case "住宿服务":
                analyzeHotelServiceWords();
                break;
            case "风景名胜":
                analyzeTravelServiceWords();
                break;
            case "商务住宅":
                break;
            case "政府机构及社会团体":
                break;
            case "科教文化服务":
                break;
            case "交通设施服务":
                analyzeTrafficServiceWords();
                break;
            case "金融保险服务":
                analyzeBankServiceWords();
                break;
            case "公司企业":
                break;
            case "道路附属设施":
                break;
            case "地名地址信息":
                break;
            case "公共设施":
                analyzeCommonalityServiceWords();
                break;
        }
    }

    /**
     * 解析不明词列表
     */
    private void analyzeUnknownWords() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < wordList.size(); i++) {
            stringBuilder.append(wordList.get(i));
        }
        locationDetailAdapterImpl.destinationSearch(stringBuilder.toString());
    }

    /**
     * 解析含有“附近”字眼的词列表
     */
    private void analyzeNearbyWords() {
        boolean startTag = false;
        StringBuilder stringBuilder = new StringBuilder();
        for (String nearbyWord : nearbyWords) {
            for (int i = 0; i < wordList.size(); i++) {
                if (startTag) {
                    stringBuilder.append(wordList.get(i));
                } else {
                    if (wordList.get(i).equals(nearbyWord)) {
                        startTag = true;
                    }
                }
            }
            if (startTag) {
                break;
            }
        }
        locationDetailAdapterImpl.nearbyPoiSearch("", stringBuilder.toString());
    }

    /**
     * 解析含有“去”“到”字眼的词列表
     */
    private void analyzeGoToWords() {
        boolean startTag = false;
        StringBuilder stringBuilder = new StringBuilder();
        for (String goToWord : goToWords) {
            for (int i = 0; i < wordList.size(); i++) {
                if (startTag) {
                    stringBuilder.append(wordList.get(i));
                } else {
                    if (wordList.get(i).equals(goToWord)) {
                        startTag = true;
                    }
                }
            }
            if (startTag) {
                break;
            }
        }
        locationDetailAdapterImpl.destinationSearch(stringBuilder.toString());
    }

    /**
     * 餐饮服务解析
     */
    private void analyzeCateringServiceWords() {
        String cateringType = poiTypes[4];
        if (wordList.contains("吃")) {
            boolean startTag = false;
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < wordList.size(); i++) {
                if (startTag) {
                    stringBuilder.append(wordList.get(i));
                } else {
                    if (wordList.get(i).equals("吃")) {
                        startTag = true;
                    }
                }
            }
            if (stringBuilder.toString().equals("东西")) {
                locationDetailAdapterImpl.nearbyPoiSearch(cateringType, "");
            } else {
                locationDetailAdapterImpl.nearbyPoiSearch(cateringType, stringBuilder.toString());
            }
        } else if (wordList.contains("喝")) {
            boolean startTag = false;
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < wordList.size(); i++) {
                if (startTag) {
                    stringBuilder.append(wordList.get(i));
                } else {
                    if (wordList.get(i).equals("喝")) {
                        startTag = true;
                    }
                }
            }
            List<String> drinks = new ArrayList<>();
            drinks.add("果汁");
            drinks.add("咖啡");
            drinks.add("奶茶");
            if (drinks.contains(stringBuilder.toString())) {
                for (int i = 0; i < drinks.size(); i++) {
                    if (stringBuilder.toString().equals(drinks.get(i))) {
                        locationDetailAdapterImpl.nearbyPoiSearch(cateringType, stringBuilder.toString());
                    }
                }
            } else {
                locationDetailAdapterImpl.nearbyPoiSearch(cateringType, "冷饮");
            }
        } else if (wordList.contains("渴") || wordList.contains("口渴") || wordList.contains("喝水")) {
            locationDetailAdapterImpl.nearbyPoiSearch(cateringType, "冷饮");
        } else {
            locationDetailAdapterImpl.nearbyPoiSearch(cateringType, "");
        }
    }

    /**
     * 购物服务解析
     */
    private void analyzeShopServiceWords() {
        String shopServiceType = poiTypes[5];
        if (wordList.contains("买")) {
            boolean startTag = false;
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < wordList.size(); i++) {
                if (startTag) {
                    stringBuilder.append(wordList.get(i));
                } else {
                    if (wordList.get(i).equals("买")) {
                        startTag = true;
                    }
                }
            }
            if (stringBuilder.toString().equals("东西")) {
                locationDetailAdapterImpl.nearbyPoiSearch(shopServiceType, "");
            } else {
                locationDetailAdapterImpl.nearbyPoiSearch(shopServiceType, stringBuilder.toString());
            }
        } else if (wordList.contains("买菜")) {
            locationDetailAdapterImpl.nearbyPoiSearch(shopServiceType, "市场");
        } else {
            locationDetailAdapterImpl.nearbyPoiSearch(shopServiceType, "");
        }
    }

    /**
     * 生活服务解析
     */
    private void analyzeLifeServiceWords() {
        String lifeServiceType = poiTypes[6];
        if (wordList.contains("邮") || wordList.contains("寄") || wordList.contains("邮寄")) {
            locationDetailAdapterImpl.nearbyPoiSearch(lifeServiceType, "快递");
        } else if (wordList.contains("理发") || wordList.contains("剪发") || wordList.contains("剪头")
                || wordList.contains("剃头") || wordList.contains("洗发") || wordList.contains("洗头")) {
            locationDetailAdapterImpl.nearbyPoiSearch(lifeServiceType, "理发");
        } else if (wordList.contains("菜鸟") || wordList.contains("驿站")) {
            locationDetailAdapterImpl.nearbyPoiSearch(lifeServiceType, "菜鸟驿站");
        } else if (wordList.contains("理发店") || wordList.contains("美发店") || wordList.contains("美容店")) {
            String keyWord = null;
            for (String word : wordList) {
                if (word.contains("店")) {
                    keyWord = word.replace("店", "");
                }
            }
            locationDetailAdapterImpl.nearbyPoiSearch(lifeServiceType, keyWord);
        } else {
            for (String keyWord : lifeServiceWords) {
                if (wordList.contains(keyWord)) {
                    locationDetailAdapterImpl.nearbyPoiSearch(lifeServiceType, keyWord);
                    break;
                }
            }
        }
    }

    /**
     * 体育休闲服务解析
     */
    private void analyzeSportServiceWords() {
        String sportServiceType = poiTypes[7];
        if (wordList.contains("骑车") || wordList.contains("骑行")) {
            locationDetailAdapterImpl.destinationSearch("绿道");
        } else if (wordList.contains("散步")) {
            locationDetailAdapterImpl.nearbyPoiSearch(poiTypes[10], "公园");
        } else if (wordList.contains("踢球") || wordList.contains("足球")) {
            locationDetailAdapterImpl.nearbyPoiSearch(sportServiceType, "足球场");
        } else if (wordList.contains("打球") || wordList.contains("运动")) {
            locationDetailAdapterImpl.nearbyPoiSearch(sportServiceType, "");
        } else if (wordList.contains("歌厅") || wordList.contains("唱歌")) {
            locationDetailAdapterImpl.nearbyPoiSearch(sportServiceType, "KTV");
        } else if (wordList.contains("跑步")) {
            locationDetailAdapterImpl.nearbyPoiSearch(sportServiceType, "田径场");
        } else if (wordList.contains("台球") || wordList.contains("桌球") || wordList.contains("台球馆") || wordList.contains("桌球馆")) {
            locationDetailAdapterImpl.nearbyPoiSearch(sportServiceType, "台球");
        } else if (wordList.contains("跳舞")) {
            locationDetailAdapterImpl.nearbyPoiSearch(sportServiceType, "舞厅");
        } else if (wordList.contains("乒乓球场") || wordList.contains("乒乓球馆") || wordList.contains("乒乓球室")) {
            locationDetailAdapterImpl.nearbyPoiSearch(sportServiceType, "乒乓球");
        } else {
            for (String keyWord : sportServiceWords) {
                if (wordList.contains(keyWord)) {
                    locationDetailAdapterImpl.nearbyPoiSearch(sportServiceType, keyWord);
                    break;
                }
            }
        }
    }

    /**
     * 医疗保健服务解析
     */
    private void analyzeMedicalServiceWords() {
        String medicalServiceType = poiTypes[8];
        if (wordList.contains("药") || wordList.contains("吃药") || wordList.contains("买药")
                || wordList.contains("药店") || wordList.contains("药房")) {
            locationDetailAdapterImpl.nearbyPoiSearch(medicalServiceType, "药店");
        } else {
            locationDetailAdapterImpl.nearbyPoiSearch(medicalServiceType, "医院");
        }
    }

    /**
     * 住宿服务解析
     */
    private void analyzeHotelServiceWords() {
        String hotelServiceType = poiTypes[9];
        locationDetailAdapterImpl.nearbyPoiSearch(hotelServiceType, "");
    }

    /**
     * 风景名胜解析
     */
    private void analyzeTravelServiceWords() {
        String travelServiceType = poiTypes[10];
        locationDetailAdapterImpl.nearbyPoiSearch(travelServiceType, "");
    }

    /**
     * 交通设施服务解析
     */
    private void analyzeTrafficServiceWords() {
        String trafficServiceType = poiTypes[14];
        if (wordList.contains("停车")) {
            locationDetailAdapterImpl.nearbyPoiSearch(trafficServiceType, "停车场");
        } else {
            for (String keyWord : trafficServiceWords) {
                if (wordList.contains(keyWord)) {
                    locationDetailAdapterImpl.nearbyPoiSearch(trafficServiceType, keyWord);
                    break;
                }
            }
        }
    }

    /**
     * 金融保险服务解析
     */
    private void analyzeBankServiceWords() {
        String bankServiceType = poiTypes[15];
        locationDetailAdapterImpl.nearbyPoiSearch(bankServiceType, "银行");
    }

    /**
     * 公共设施解析
     */
    private void analyzeCommonalityServiceWords() {
        String commonalityServiceType = poiTypes[19];
        locationDetailAdapterImpl.nearbyPoiSearch(commonalityServiceType, "厕所");
    }

    /**
     * 判断是否为“买药”之类的特殊情况
     *
     * @param wordList 关键词列表
     * @return 布尔值
     */
    private boolean isSpecialCondition(List<String> wordList) {
        return wordList.contains("买") && wordList.contains("药");
    }

    /**
     * 判断是否有“去”“到”等字眼
     *
     * @param wordList 关键词列表
     * @return 布尔值
     */
    private boolean isContainsGoToWords(List<String> wordList) {
        for (String goToWord : goToWords) {
            if (wordList.contains(goToWord)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否有“附近”“周边”等字眼
     *
     * @param wordList 关键词列表
     * @return 布尔值
     */
    private boolean isContainsNearbyWords(List<String> wordList) {
        for (String nearbyWord : nearbyWords) {
            if (wordList.contains(nearbyWord)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对应特殊情况返回相应的类型
     *
     * @param wordList 关键词列表
     * @return 特殊情况类型
     */
    private String chooseSpecialConditionType(List<String> wordList) {
        String matchedType = null;
        if (wordList.contains("买") && wordList.contains("药")) {
            matchedType = poiTypes[8];
        }
        return matchedType;
    }

    /**
     * LocationFragment实现此接口以完成相应操作
     */
    public interface LocationDetailAdapterImpl {

        void nearbyPoiSearch(String poiType, String keyWord);

        void destinationSearch(String keyWord);

    }
}
