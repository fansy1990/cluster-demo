package org.scnu.util;

import org.junit.Assert;
import org.junit.Test;
import org.scnu.model.RemoteHost;

/**
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/29 下午3:56.
 */
public class ScpToTest {

    @Test
    public void test(){
        RemoteHost remoteHost= new RemoteHost("root","123456","master",22);
        String localFile = "a.txt";
        String remoteFile = "/tmp/a.txt";
        ScpTo.run(remoteHost,remoteFile,localFile);

        Assert.assertTrue(new java.io.File(localFile).exists());
        Assert.assertTrue(new java.io.File(localFile).length() > 0);
    }
}
