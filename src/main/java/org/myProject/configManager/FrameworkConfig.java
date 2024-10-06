package org.myProject.configManager;

import org.aeonbits.owner.Config;


@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:${user.dir}/src/main/resources/config.properties",
        "file:${user.dir}/src/main/resources/ENV/testprod.properties",
        "file:${user.dir}/src/main/resources/ENV/production.properties",
        "file:${user.dir}/src/main/resources/ENV/preprod.properties"

})
public interface FrameworkConfig extends Config {

    @DefaultValue("chrome")
    String browserName();
   @DefaultValue("testprod")
    String environment();
    @DefaultValue("local")
    String runMode();

    String remoteURL();

    String remotePORT();


    @Key("${environment}.url")
    String url();

    @Key("${environment}.userId")
    String userId();
    @Key("${environment}.password")
    String password();

    boolean isHeadless();

    boolean enableRecordVideo();

    boolean isSendEmail();

    String[] emailTo();

    int retryCounter();
    String selfHealing();
}
