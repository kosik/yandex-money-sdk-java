/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 NBCO Yandex.Money LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.yandex.money.api.methods;

import com.yandex.money.api.model.Error;
import com.yandex.money.api.net.MethodResponse;

import java.math.BigDecimal;

/**
 * Base class for request payment operations.
 *
 * @author Slava Yasevich (vyasevich@yamoney.ru)
 */
public abstract class BaseRequestPayment implements MethodResponse {

    protected static final String MEMBER_STATUS = "status";
    protected static final String MEMBER_ERROR = "error";
    protected static final String MEMBER_REQUEST_ID = "request_id";
    protected static final String MEMBER_CONTRACT_AMOUNT = "contract_amount";

    public final Status status;
    public final Error error;
    public final String requestId;
    public final BigDecimal contractAmount;

    /**
     * Constructor.
     *
     * @param status status of the request
     * @param error error code
     * @param requestId unique request id
     * @param contractAmount contract amount
     */
    protected BaseRequestPayment(Status status, Error error, String requestId,
                                 BigDecimal contractAmount) {

        if (status == Status.SUCCESS && requestId == null) {
            throw new IllegalArgumentException("requestId is null when status is success");
        }
        this.status = status;
        this.error = error;
        this.requestId = requestId;
        this.contractAmount = contractAmount;
    }

    @Override
    public String toString() {
        return "BaseRequestPayment{" +
                "status=" + status +
                ", error=" + error +
                ", requestId='" + requestId + '\'' +
                ", contractAmount=" + contractAmount +
                '}';
    }

    public enum Status {
        SUCCESS(CODE_SUCCESS),
        REFUSED(CODE_REFUSED),
        HOLD_FOR_PICKUP(CODE_HOLD_FOR_PICKUP),
        UNKNOWN(CODE_UNKNOWN);

        public final String code;

        Status(String code) {
            this.code = code;
        }

        public static Status parse(String status) {
            for (Status value : values()) {
                if (value.code.equals(status)) {
                    return value;
                }
            }
            return UNKNOWN;
        }
    }
}
