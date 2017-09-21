package common.constant;

import java.io.Serializable;

/**
 * Description： 竞价模块静态常量
 *
 * @author zhang shubin
 * @date 2017年5月31日 下午2:30:53
 * @since JDK1.7
 */
public class OnlineBidding implements Serializable {

    private static final long serialVersionUID = 7300577369126159784L;

    /**
     * 资源服务中心标识
     */
    public static final String AUTH_TYPE = "authType";

    /**
     * 执行成功标识
     */
    public static final String SUCCESS = "success";

    /**
     * 执行失败标识
     */
    public static final String ERROR = "error";

    /**
     * 目录名称标识
     */
    public static final String CATEGORY_NAME = "categoryName";

    /**
     * 定型产品对象标识
     */
    public static final String OB_PRODUCT = "obProduct";

    /**
     * 供应商名称标识
     */
    public static final String SUPPLIER_NAME = "supplierName";

    /**
     * 状态标识
     */
    public static final String STATUS = "status";

    /**
     * 目录末节点标识
     */
    public static final String SMALL_POINTS_ID = "smallPointsId";

    /**
     * 采购机构标识
     */
    public static final String PROCUREMENT_ID = "procurementId";

    /**
     * 目录ID标识
     */
    public static final String CATEGORY = "category";

    /**
     * 目录等级标识
     */
    public static final String CATEGORY_LEVEL = "categoryLevel";

    /**
     * 产品规格型号标识
     */
    public static final String STANDARD_MODEL = "standardModel";

    /**
     * 产品规格型号标识
     */
    public static final String QUALITY_TECHNICAL_STANDARD = "qualityTechnicalStandard";

    /**
     * 文本域字数限制标识
     */
    public static final String ERROR_WORDS = "不能超过1000个字";

    public static final String ERROR_WORDS1 = "产品代码不能重复";

    public static final String ERROR_WORDS2 = "产品名称不能重复";

    public static final String ERROR_WORDS3 = "产品目录不能为空";

    public static final String ERROR_WORDS4 = "该目录已有采购机构";
    public static final String ERROR_WORDS5 = "产品代码不能为空";
    public static final String ERROR_WORDS6 = "产品名称不能为空";
    public static final String ERROR_WORDS7 = "采购机构不能为空";

}
