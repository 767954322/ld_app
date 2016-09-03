package com.autodesk.shejijia.consumer.personalcenter.designer.entity;

import java.io.Serializable;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date    16-6-7
 * @file    RealName.java  .
 * @brief    .
 */
public class RealName implements Serializable {

    private Object audit_date;
    private String audit_status;
    private Object auditor;
    private Object auditor_opinion;
    private Object birthday;
    private String certificate_no;
    private Object certificate_type;
    private int is_loho;
    private String mobile_number;
    /**
     * file_id : 17984326
     * file_name : 1453650705.jpg
     * file_url : http://s3.cn-north-1.amazonaws.com.cn/sherpainternal-standard/Internal/Beta/2016/03/05__03/1453650705.jpg56da597ab9eaf-56da597ab9f54.jpg?X-Amz-Content-Sha256=e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAPCHQ5JOAZDJNMKAA%2F20160305%2Fcn-north-1%2Fs3%2Faws4_request&X-Amz-Date=20160305T035851Z&X-Amz-SignedHeaders=Host&X-Amz-Expires=300&X-Amz-Signature=5a2f0f71329a1446e5cfc716300614d38f212dbb914ea4e0e2823c099d613168
     */

    private PhotoBackEndEntity photo_back_end;
    /**
     * file_id : 17984325
     * file_name : 1455162706.jpg
     * file_url : http://s3.cn-north-1.amazonaws.com.cn/sherpainternal-standard/Internal/Beta/2016/03/05__03/1455162706.jpg56da59729c595-56da59729c63a.jpg?X-Amz-Content-Sha256=e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAPCHQ5JOAZDJNMKAA%2F20160305%2Fcn-north-1%2Fs3%2Faws4_request&X-Amz-Date=20160305T035843Z&X-Amz-SignedHeaders=Host&X-Amz-Expires=300&X-Amz-Signature=a0308c7cde05055adc488f9bb25652cb4a0f46083a069a9f66f8d20ea7cc9b26
     */

    private PhotoFrontEndEntity photo_front_end;
    /**
     * file_id : 17984327
     * file_name : 1453650285.jpg
     * file_url : http://s3.cn-north-1.amazonaws.com.cn/sherpainternal-standard/Internal/Beta/2016/03/05__03/1453650285.jpg56da5981edb24-56da5981edbca.jpg?X-Amz-Content-Sha256=e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAPCHQ5JOAZDJNMKAA%2F20160305%2Fcn-north-1%2Fs3%2Faws4_request&X-Amz-Date=20160305T035858Z&X-Amz-SignedHeaders=Host&X-Amz-Expires=300&X-Amz-Signature=174250f72c78b71cc7654e47aa8e3b1f0b6e869413840cb19e3a53db0d378a44
     */

    private PhotoInHandEntity photo_in_hand;
    private String real_name;

    public void setAudit_date(Object audit_date) {
        this.audit_date = audit_date;
    }

    public void setAudit_status(String audit_status) {
        this.audit_status = audit_status;
    }

    public void setAuditor(Object auditor) {
        this.auditor = auditor;
    }

    public void setAuditor_opinion(Object auditor_opinion) {
        this.auditor_opinion = auditor_opinion;
    }

    public void setBirthday(Object birthday) {
        this.birthday = birthday;
    }

    public void setCertificate_no(String certificate_no) {
        this.certificate_no = certificate_no;
    }

    public void setCertificate_type(Object certificate_type) {
        this.certificate_type = certificate_type;
    }

    public void setIs_loho(int is_loho) {
        this.is_loho = is_loho;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public void setPhoto_back_end(PhotoBackEndEntity photo_back_end) {
        this.photo_back_end = photo_back_end;
    }

    public void setPhoto_front_end(PhotoFrontEndEntity photo_front_end) {
        this.photo_front_end = photo_front_end;
    }

    public void setPhoto_in_hand(PhotoInHandEntity photo_in_hand) {
        this.photo_in_hand = photo_in_hand;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public Object getAudit_date() {
        return audit_date;
    }

    public String getAudit_status() {
        return audit_status;
    }

    public Object getAuditor() {
        return auditor;
    }

    public Object getAuditor_opinion() {
        return auditor_opinion;
    }

    public Object getBirthday() {
        return birthday;
    }

    public String getCertificate_no() {
        return certificate_no;
    }

    public Object getCertificate_type() {
        return certificate_type;
    }

    public int getIs_loho() {
        return is_loho;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public PhotoBackEndEntity getPhoto_back_end() {
        return photo_back_end;
    }

    public PhotoFrontEndEntity getPhoto_front_end() {
        return photo_front_end;
    }

    public PhotoInHandEntity getPhoto_in_hand() {
        return photo_in_hand;
    }

    public String getReal_name() {
        return real_name;
    }

    public static class PhotoBackEndEntity {
        private String file_id;
        private String file_name;
        private String file_url;

        public void setFile_id(String file_id) {
            this.file_id = file_id;
        }

        public void setFile_name(String file_name) {
            this.file_name = file_name;
        }

        public void setFile_url(String file_url) {
            this.file_url = file_url;
        }

        public String getFile_id() {
            return file_id;
        }

        public String getFile_name() {
            return file_name;
        }

        public String getFile_url() {
            return file_url;
        }
    }

    public static class PhotoFrontEndEntity {
        private String file_id;
        private String file_name;
        private String file_url;

        public void setFile_id(String file_id) {
            this.file_id = file_id;
        }

        public void setFile_name(String file_name) {
            this.file_name = file_name;
        }

        public void setFile_url(String file_url) {
            this.file_url = file_url;
        }

        public String getFile_id() {
            return file_id;
        }

        public String getFile_name() {
            return file_name;
        }

        public String getFile_url() {
            return file_url;
        }
    }

    public static class PhotoInHandEntity {
        private String file_id;
        private String file_name;
        private String file_url;

        public void setFile_id(String file_id) {
            this.file_id = file_id;
        }

        public void setFile_name(String file_name) {
            this.file_name = file_name;
        }

        public void setFile_url(String file_url) {
            this.file_url = file_url;
        }

        public String getFile_id() {
            return file_id;
        }

        public String getFile_name() {
            return file_name;
        }

        public String getFile_url() {
            return file_url;
        }
    }
}
