package gunstar.api;

import gunstar.GunstarContext;
import gunstar.api.gm.GamemasterAPI;
import gunstar.api.gm.InstanceStatus;
import gunstar.api.gm.LevelInfo;
import org.junit.Ignore;
import org.junit.Test;

public class GamemasterIT {

    public final GunstarContext gunstarContext = new GunstarContext("api_key_here");

    public final GamemasterAPI api = new GamemasterAPI(gunstarContext);

    @Test
    @Ignore("gm api currently borked, putting it under even more stress is considered unfriendly")
    public void instanceLifecycle() {
        LevelInfo levelInfo = api.start("first_steps");
        QAHelper.assertOk(levelInfo);
        BaseResponse baseResponse = api.restart(levelInfo.instanceId);
        QAHelper.assertOk(baseResponse);
        levelInfo = api.resume(levelInfo.instanceId);
        QAHelper.assertOk(levelInfo);
        InstanceStatus instanceStatus = api.getStatus(levelInfo.instanceId);
        QAHelper.assertOk(instanceStatus);
        baseResponse = api.stop(levelInfo.instanceId);
        QAHelper.assertOk(baseResponse);
    }
}
