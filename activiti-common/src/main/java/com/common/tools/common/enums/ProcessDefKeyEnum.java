package com.common.tools.common.enums;

/**
 * Created by jingyan on 2016/12/14.
 */
public enum ProcessDefKeyEnum {

    FXSX(100, "work_flow_v1.0"),    //风险授信
    DKSQ(101, "work_flow_v2.0");    //贷款申请

    private final int code;
    private final String processDefKey;

    ProcessDefKeyEnum(int code, String processDefKey) {
        this.code = code;
        this.processDefKey = processDefKey;
    }

    public static ProcessDefKeyEnum valueOf(int code) {
        for (ProcessDefKeyEnum pdk : ProcessDefKeyEnum.values()) {
            if (pdk.code == code) {
                return pdk;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getProcessDefKey() {
        return processDefKey;
    }
}
