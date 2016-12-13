package com.autodesk.shejijia.shared.components.issue.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by allengu on 16-12-13.
 */
public class IssueFileBean implements Serializable {

    private List<Files> files;

    public IssueFileBean(List<Files> files) {
        this.files = files;
    }

    public List<Files> getFiles() {
        return files;
    }

    public void setFiles(List<Files> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "IssueFileBean{" +
                "files=" + files +
                '}';
    }

    public class Files implements Serializable {

        private String name;
        private String file_id;
        private String public_url;

        public Files(String name, String file_id, String public_url) {
            this.name = name;
            this.file_id = file_id;
            this.public_url = public_url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFile_id() {
            return file_id;
        }

        public void setFile_id(String file_id) {
            this.file_id = file_id;
        }

        public String getPublic_url() {
            return public_url;
        }

        public void setPublic_url(String public_url) {
            this.public_url = public_url;
        }

        @Override
        public String toString() {
            return "IssueFiles{" +
                    "name='" + name + '\'' +
                    ", file_id='" + file_id + '\'' +
                    ", public_url='" + public_url + '\'' +
                    '}';
        }
    }


}
