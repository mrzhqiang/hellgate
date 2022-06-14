package hellgate.common.script;

import lombok.Data;

/**
 * 剧本角色数据。
 */
@Data
public class ScriptRoleData {

    /**
     * 角色名称。
     */
    private String name;
    /**
     * 角色类型。
     */
    private String type;
    /**
     * 初始生命值。
     */
    private int initHp;
    /**
     * 成长生命值。
     */
    private int growHp;
    /**
     * 生命值转化比率。
     */
    private int hpRatio;
    /**
     * 初始魔法值。
     */
    private int initMp;
    /**
     * 成长魔法值。
     */
    private int growMp;
    /**
     * 魔法值转化比率。
     */
    private int mpRatio;
    /**
     * 初始力量属性。
     */
    private int initSTR;
    /**
     * 力量属性转化比率。
     */
    private int strRatio;
    /**
     * 初始敏捷属性。
     */
    private int initDEX;
    /**
     * 敏捷属性转化比率。
     */
    private int dexRatio;
    /**
     * 初始智力属性。
     */
    private int initINT;
    /**
     * 智力属性转化比率。
     */
    private int intRatio;
    /**
     * 初始体质属性。
     */
    private int initCON;
    /**
     * 体质属性转化比率。
     */
    private int conRatio;
}
