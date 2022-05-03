package com.ssz.common.web.util;

import com.google.common.base.Charsets;
import org.apache.commons.lang3.time.DateUtils;

import java.time.LocalDate;
import java.util.UUID;


public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static final String EMPTY = "";
    public static final String ADD_SIGN = "+";
    public static final String MINUS_SIGN = "-";
    public static final String MUL_SIGN = "*";
    public static final String DIV_SIGN = "/";
    public static final String UNDER_LINE = "_";

    public static final String COMMA_EN = ",";
    public static final String UTF_8 = Charsets.UTF_8.displayName();
    private static final String MERCHANT_CODE_TEMPLATE = "%s%02d%04d";

    private static final String PRODUCT_CODE_TEMPLATE = "%s%06d";
    private static final String PRODUCT_SETTLE_CODE_TEMPLATE = "%04d%s%04d";

    private static final String INVENTORY_SN_TEMPLATE = "%s%06d";

    private static final String OPERATE_CODE_TEMPLATE = "%s%05d%04d";

    /**
     * 生成商户编号: 年月日[20190101] + 商户类型[01] + 流水号[0001]
     * @param merchantType
     * @param sn
     * @return
     */
//    public static String generateMerchantCode(int merchantType, int sn) {
//        checkServerArgument(sn < 10000, "当天商户流水号大于 10000");
//        return String.format(MERCHANT_CODE_TEMPLATE, DateUtils.localDate2String(LocalDate.now(), DateUtils.SHORT_DATE_PATTERN), merchantType, sn);
//    }

    /**
     * 生成商品编码: 年月日[20190101] + 流水号[000001]
     *
     * @return
     */
    public static String generateProductCode() {
        return String.format(PRODUCT_CODE_TEMPLATE, UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10), System.currentTimeMillis());
    }

    /**
     * 生成商品系统结算码: 年月日[191101] + 流水号[000001]
     * @param sn
     * @return
     */
//    public static String generateSystemSettleCode(Long merchantId, int sn) {
//        checkServerArgument(sn < 1000000, "当天商品流水号大于 1000000");
//        return String.format(PRODUCT_SETTLE_CODE_TEMPLATE, merchantId, DateUtils.localDate2String(LocalDate.now(), DateUtils.SMALL_SHORT_DATE_PATTERN), sn);
//    }

    /**
     * 生成库存系统盘货单号: zs- + 年月日[20191101] + 流水号[000001]
     * @param sn
     * @return
     */
//    public static String generateInventorySN(int sn) {
//        checkServerArgument(sn < 1000000, "当天流水号大于 1000000");
//        return String.format(INVENTORY_SN_TEMPLATE, DateUtils.localDate2String(LocalDate.now(), DateUtils.SHORT_DATE_PATTERN), sn);
//    }

    /**
     * 生成设备模块运营编号: 9 + 5位商户号 + 流水号[00001]
     * @param deviceCount
     * @return
     */
//    public static String generateOperateCode(Long merchantId, int deviceCount) {
//        checkServerArgument(merchantId < 100000, "商户 id 大于 99999");
//        checkServerArgument(deviceCount < 10000, "商户设备数量大于 9999");
//        return String.format(OPERATE_CODE_TEMPLATE, 9, merchantId, deviceCount);
//    }

    /**
     * 生成订单 id
     *
     * @param deviceCode
     * @param ids
     * @return
     */
//    public static List<String> generateOrderIds(String deviceCode, List<String> ids) {
//        if (CollectionUtils.isEmpty(ids)) return Lists.newArrayList();
//        return ids.stream().map(id -> String.format("%s%s%s", id.substring(0, 14), deviceCode, id.substring(14))).collect(Collectors.toList());
//    }
}
