package commons;


public interface Const {

    interface Json {
        String MANIFEST = "manifest";

        interface Format {
            String DATE_FORMAT = "yyyy-MM-dd";
        }
    }

    interface Report {
        String REPORT_SWAP_PATH = java.lang.System.getProperty("catalina.home") + "/temp/swap";
    }

    interface Log {
        String DATE_FORMAT = "yyyy-MM-dd";
    }

    interface ResponseParam {
        int LIMIT = 100;
    }
}
