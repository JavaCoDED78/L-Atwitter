package com.gmail.javacoded78.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

    private String to;
    private String subject;
    private String template;
    private Map<String, Object> attributes;

    public static EmailRequestBuilder builder() {
        return new EmailRequestBuilder();
    }

    public static class EmailRequestBuilder {
        private String to;
        private String subject;
        private String template;
        private Map<String, Object> attributes;

        EmailRequestBuilder() {
        }

        public EmailRequestBuilder to(String to) {
            this.to = to;
            return this;
        }

        public EmailRequestBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public EmailRequestBuilder template(String template) {
            this.template = template;
            return this;
        }

        public EmailRequestBuilder attributes(Map<String, Object> attributes) {
            this.attributes = attributes;
            return this;
        }

        public EmailRequest build() {
            return new EmailRequest(this.to, this.subject, this.template, this.attributes);
        }

        public String toString() {
            return "EmailRequest.EmailRequestBuilder(to=" + this.to + ", subject=" + this.subject + ", template=" + this.template + ", attributes=" + this.attributes + ")";
        }
    }
}
