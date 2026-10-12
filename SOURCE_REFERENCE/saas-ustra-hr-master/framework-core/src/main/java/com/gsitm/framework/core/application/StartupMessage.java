package com.gsitm.framework.core.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 애플리케이션 기동이 완료되면 로고 + 기동 정보를 콘솔에 출력한다.
 *
 * <p>Develop / Production 주소는 아직 확정되지 않아 "(추후 업데이트 예정)" 으로 출력된다.
 * 주소가 정해지면 아래 프로퍼티만 기입하면 된다.
 * <pre>
 * ustra:
 *   banner:
 *     develop-url: https://...
 *     production-url: https://...
 * </pre>
 */
@Component
public class StartupMessage {

    private static final String GREEN_COLOR = "\u001B[32m";
    private static final String BLUE_COLOR = "\u001B[36m";
    private static final String DEFAULT_COLOR = "\u001B[0m";

    /** 로고 영역 폭. 오른쪽 정보 컬럼의 시작 위치를 결정한다. */
    private static final int LOGO_WIDTH = 36;

    /** 정보 컬럼 라벨 폭. 라벨 뒤에 ": " 가 붙는다. */
    private static final int LABEL_WIDTH = 19;

    private static final String[] LOGO = {
            " _   _   ____ _____ ____      _",
            "| | | | / ___|_   _|  _ \\    / \\",
            "| | | | \\___ \\ | | | |_) |  / _ \\",
            "| |_| |_ ___) || | |  _ <  / ___ \\",
            " \\___/(_)____/ |_| |_| \\_\\/_/   \\_\\",
            " :: USTRA HR Framework ::"
    };

    private final Environment environment;

    /**
     * 현재 활성화된 환경 프로파일(local / dev1 / dev2 / prod1 / prod2).
     * <p>모듈 프로파일(core/web/api)까지 전부 나열하지 않고 환경 프로파일만 보여주기 위해
     * 활성화된 설정 문서의 {@code spring.config.activate.on-profile} 값을 그대로 사용한다.
     * 값이 없으면 {@code spring.profiles.active} 목록으로 대체한다.
     */
    @Value("${spring.config.activate.on-profile:}")
    private String onProfile;

    /** 실제 주소가 확정되면 {@code ustra.banner.develop-url} 프로퍼티로 기입한다. */
    @Value("${ustra.banner.develop-url:(추후 업데이트 예정)}")
    private String developUrl;

    /** 실제 주소가 확정되면 {@code ustra.banner.production-url} 프로퍼티로 기입한다. */
    @Value("${ustra.banner.production-url:(추후 업데이트 예정)}")
    private String productionUrl;

    public StartupMessage(Environment environment) {
        this.environment = environment;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady(ApplicationReadyEvent event) {
        String[] info = {
                format("Active Profiles", activeProfiles()),
                format("Startup Time", startupTime(event.getTimeTaken())),
                format("Local Access", localAccess(event.getApplicationContext())),
                format("Develop Access", developUrl),
                format("Production Access", productionUrl),
                format("Environment", "Java " + System.getProperty("java.version")
                        + " / Spring Boot " + SpringBootVersion.getVersion())
        };

        StringBuilder banner = new StringBuilder(System.lineSeparator());
        for (int i = 0; i < LOGO.length; i++) {
            banner.append(GREEN_COLOR)
                  .append(String.format("%-" + LOGO_WIDTH + "s", LOGO[i]))
                  .append(BLUE_COLOR)
                  .append(i < info.length ? info[i] : "")
                  .append(DEFAULT_COLOR)
                  .append(System.lineSeparator());
        }

        System.out.println(banner);
        System.out.flush();
    }

    private static String format(String label, String value) {
        return String.format("%-" + LABEL_WIDTH + "s: %s", label, value);
    }

    private String activeProfiles() {
        if (!onProfile.isBlank()) {
            return onProfile;
        }
        String[] profiles = environment.getActiveProfiles();
        if (profiles.length == 0) {
            profiles = environment.getDefaultProfiles();
        }
        return String.join(", ", profiles);
    }

    private static String startupTime(Duration timeTaken) {
        if (timeTaken == null) {
            return "unknown";
        }
        return String.format("%.2f seconds", timeTaken.toMillis() / 1000d);
    }

    private String localAccess(ApplicationContext context) {
        int port = -1;
        if (context instanceof WebServerApplicationContext webContext && webContext.getWebServer() != null) {
            port = webContext.getWebServer().getPort();
        }
        if (port <= 0) {
            port = environment.getProperty("server.port", Integer.class, 8080);
        }

        String contextPath = environment.getProperty("server.servlet.context-path", "");
        if (contextPath.equals("/")) {
            contextPath = "";
        }
        return "http://localhost:" + port + contextPath;
    }
}
