package gunstar.api.gm;

import gunstar.GunstarContext;
import gunstar.api.AbstractAPI;
import gunstar.api.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Public (but not officially documented) game coordination API.
 */
public class GamemasterAPI extends AbstractAPI {

    private static final Logger logger = LoggerFactory.getLogger(GamemasterAPI.class);

    public GamemasterAPI(GunstarContext context) {
        super(context.restTemplate, context.gmRootUrl, logger);
    }

    // will resume a level instead if it's already running.
    public LevelInfo start(String name) {
        return post(apiRootUrl + "/levels/" + name, null, LevelInfo.class);
    }

    public LevelInfo resume(int instanceId) {
        return post(apiRootUrl + "/instances/" + instanceId + "/resume", LevelInfo.class);
    }

    public InstanceStatus getStatus(int instanceId) {
        return get(apiRootUrl + "/instances/" + instanceId, InstanceStatus.class);
    }

    public BaseResponse stop(int instanceId) {
        return post(apiRootUrl + "/instances/" + instanceId + "/stop", BaseResponse.class);
    }

    public LevelInfo restart(int instanceId) {
        return post(apiRootUrl + "/instances/" + instanceId + "/restart", LevelInfo.class);
    }
}