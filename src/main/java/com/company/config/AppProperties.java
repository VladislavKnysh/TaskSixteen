package com.company.config;

import com.company.annotstions.SystemProp;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppProperties {
    @SystemProp("app.service.workmode")
    private String workMode;

    @SystemProp("api.base-uri")
    private String uri;

    @SystemProp("file.path")
    private String fileName;
}
