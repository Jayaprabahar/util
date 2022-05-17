package com.jayaprabahar.util.cloud.aws.s3;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;

/**
 * <p> Project : com.jayaprabahar.util.cloud.aws.s3 </p>
 * <p> Title : AwsS3PresignedUrlValidator.java </p>
 * <p> Description: Validates AWS S3 bucket Presigned URL. Few more validations can be added specific to the project</p>
 * <p> Created: Ma7 17, 2022 </p>
 *
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 */
@UtilityClass
@Setter
public class AwsS3PresignedUrlValidator {

    public static final String X_AMZ_ALGORITHM = "AWS4-HMAC-SHA256";
    public static final String X_AMZ_SIGNED_HEADERS = "host";
    public static final String X_AMZ_CREDENTAIL_SUFFIX_3 = "s3";
    public static final String X_AMZ_CREDENTAIL_SUFFIX_4 = "aws4_request";
    public static final DateTimeFormatter AWZ_DATE_FORMATTER = new DateTimeFormatterBuilder()
            .append(DateTimeFormatter.BASIC_ISO_DATE)
            .appendLiteral('T')
            .appendValue(HOUR_OF_DAY, 2)
            .appendValue(MINUTE_OF_HOUR, 2)
            .appendValue(SECOND_OF_MINUTE, 2)
            .appendZoneRegionId()
            .toFormatter();

    String s3BucketRegion;
    String urlExpiryTimeInSecs;
    String fileName;

    public boolean isValidAwsS3PresignedUrl(String urlToCheck) {
        if (StringUtils.isNotBlank(urlToCheck)) {
            List<NameValuePair> urlParamPairs = URLEncodedUtils.parse(URI.create(urlToCheck), StandardCharsets.UTF_8);

            if (CollectionUtils.isNotEmpty(urlParamPairs) && urlToCheck.contains(fileName)) {
                Map<String, String> mappedHeaders = urlParamPairs.stream().collect(Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));

                return X_AMZ_ALGORITHM.equals(mappedHeaders.get("X-Amz-Algorithm"))
                        && isValidAwsDate(mappedHeaders.get("X-Amz-Date"))
                        && X_AMZ_SIGNED_HEADERS.equals(mappedHeaders.get("X-Amz-SignedHeaders"))
                        && urlExpiryTimeInSecs.equals(mappedHeaders.get("X-Amz-Expires"))
                        && isValidCredentialFormat(mappedHeaders.get("X-Amz-Credential"))
                        && StringUtils.isNotBlank(mappedHeaders.get("X-Amz-Signature"));
            }
        }
        return false;
    }

    public boolean isValidCredentialFormat(String credential) {
        if (StringUtils.isNotBlank(credential)) {
            String result = URLDecoder.decode(credential, StandardCharsets.UTF_8);
            String[] credentialParts = result.split("/");

            return credentialParts.length == 5
                    && StringUtils.isNotBlank(credentialParts[0])
                    && LocalDate.parse(credentialParts[1], DateTimeFormatter.BASIC_ISO_DATE).until(LocalDate.now()).getDays() == 0
                    && StringUtils.equals(s3BucketRegion, credentialParts[2])
                    && StringUtils.equals(X_AMZ_CREDENTAIL_SUFFIX_3, credentialParts[3])
                    && StringUtils.equals(X_AMZ_CREDENTAIL_SUFFIX_4, credentialParts[4]);
        }
        return false;
    }

    public boolean isValidAwsDate(String awzDate) {
        return Duration.between(parseAwzDate(awzDate), Instant.now()).getSeconds() > 0;
    }

    public Instant parseAwzDate(String awzDate) {
        return Instant.from(AWZ_DATE_FORMATTER.parse(awzDate));
    }

}
