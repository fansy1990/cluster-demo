package org.scnu.util;

import org.junit.Assert;
import org.junit.Test;
import org.scnu.model.RemoteHost;

/**
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/29 下午3:56.
 */
public class SshExecTest {

    @Test
    public void test(){
        RemoteHost remoteHost= new RemoteHost("root","123456","master",22);
        String command = "ls /tmp/a.txt";

        Assert.assertTrue(SshExec.run(remoteHost,command).getExitCode() ==0);

    }
}
