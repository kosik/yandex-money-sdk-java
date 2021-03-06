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

package com.yandex.money.api.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.yandex.money.api.methods.JsonUtils;

import java.lang.reflect.Type;
import java.math.BigDecimal;

/**
 * Detailed balance info.
 *
 * @author Slava Yasevich (vyasevich@yamoney.ru)
 */
public class BalanceDetails {

    /**
     * total balance
     */
    public final BigDecimal total;

    /**
     * available balance
     */
    public final BigDecimal available;

    /**
     * pending deposition
     */
    public final BigDecimal depositionPending;

    /**
     * money blocked
     */
    public final BigDecimal blocked;

    /**
     * account's debt
     */
    public final BigDecimal debt;

    /**
     * money on hold
     */
    public final BigDecimal hold;

    /**
     * Constructor
     *
     * @param total total balance
     * @param available available balance
     * @param depositionPending  pending deposition
     * @param blocked money blocked
     * @param debt account's debt
     */
    public BalanceDetails(BigDecimal total, BigDecimal available, BigDecimal depositionPending,
                          BigDecimal blocked, BigDecimal debt, BigDecimal hold) {

        if (total == null) {
            throw new JsonParseException("balance total is null");
        }
        this.total = total;
        if (available == null) {
            throw new JsonParseException("balance available is null");
        }
        this.available = available;
        this.depositionPending = depositionPending;
        this.blocked = blocked;
        this.debt = debt;
        this.hold = hold;
    }

    /**
     * Creates {@link com.yandex.money.api.model.BalanceDetails} from JSON.
     *
     * @param element JSON object
     * @return {@link com.yandex.money.api.model.BalanceDetails}
     */
    public static BalanceDetails createFromJson(JsonElement element) {
        return buildGson().fromJson(element, BalanceDetails.class);
    }

    @Override
    public String toString() {
        return "BalanceDetails{" +
                "total=" + total +
                ", available=" + available +
                ", depositionPending=" + depositionPending +
                ", blocked=" + blocked +
                ", debt=" + debt +
                ", hold=" + hold +
                '}';
    }

    private static Gson buildGson() {
        return new GsonBuilder()
                .registerTypeAdapter(BalanceDetails.class, new Deserializer())
                .create();
    }

    private static final class Deserializer implements JsonDeserializer<BalanceDetails> {
        @Override
        public BalanceDetails deserialize(JsonElement json, Type typeOfT,
                                          JsonDeserializationContext context)
                throws JsonParseException {

            JsonObject object = json.getAsJsonObject();
            return new BalanceDetails(JsonUtils.getMandatoryBigDecimal(object, "total"),
                    JsonUtils.getMandatoryBigDecimal(object, "available"),
                    JsonUtils.getBigDecimal(object, "deposition_pending"),
                    JsonUtils.getBigDecimal(object, "blocked"),
                    JsonUtils.getBigDecimal(object, "debt"),
                    JsonUtils.getBigDecimal(object, "hold"));
        }
    }
}
