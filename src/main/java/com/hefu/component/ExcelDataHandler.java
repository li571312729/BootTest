package com.hefu.component;

import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;

/**
 * 导出拦截器
 * @author xiaoqiangli
 * @Date 2022-02-09
 */
public class ExcelDataHandler extends ExcelDataHandlerDefaultImpl<Object> {

    @Override
    public Hyperlink getHyperlink(CreationHelper creationHelper, Object obj, String name, Object value) {
        Hyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.FILE);
        hyperlink.setLabel(name);
        hyperlink.setAddress((String) value);
        return hyperlink;
    }
}
