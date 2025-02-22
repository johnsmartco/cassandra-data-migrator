package com.datastax.cdm.data;

import com.datastax.oss.driver.api.core.cql.AsyncResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

import java.util.concurrent.CompletionStage;

public class Record {
    public enum Diff {
        UNKNOWN,
        NONE,
        MISSING_TARGET,
        FIELD_DIFF
    }

    private EnhancedPK pk;
    private Row originRow;
    private Row targetRow;
    private CompletionStage<AsyncResultSet> targetFutureRow;
    private Diff diff = Diff.UNKNOWN;

    public Record(EnhancedPK pk, Row originRow, Row targetRow, CompletionStage<AsyncResultSet> targetFutureRow) {
        if (null == pk || (null == originRow && null == targetRow && null == targetFutureRow)) {
            throw new IllegalArgumentException("pk and at least one row must be provided");
        }
        this.pk = pk;
        this.originRow = originRow;
        this.targetRow = targetRow;
        this.targetFutureRow = targetFutureRow;
    }

    public Record(EnhancedPK pk, Row originRow, Row targetRow) {
        this(pk, originRow, targetRow, null);
    }

    public Record(EnhancedPK pk, CompletionStage<AsyncResultSet> targetFutureRow) {
        this(pk, null, null, targetFutureRow);
    }

    public EnhancedPK getPk() {return pk;}
    public Row getOriginRow() {return originRow;}
    public Row getTargetRow() {
        if (null == targetRow && null != targetFutureRow) {
            AsyncResultSet asyncResultSet = targetFutureRow.toCompletableFuture().join();
            targetRow = asyncResultSet.one();
        }
        return targetRow;
    }

    public void setTargetRow(Row targetRow) {this.targetRow = targetRow;}
    public void setAsyncTargetRow(CompletionStage<AsyncResultSet> targetFutureRow) {
        this.targetRow = null;
        this.targetFutureRow = targetFutureRow;
    }

    public boolean isValid() {
        return null != pk && (null != originRow || null != targetRow || null != targetFutureRow) && !pk.isError();
    }

    @Override
    public String toString() {
        return "Record{" +
                "pk=" + pk +
                ", originRow is " + ((null==originRow) ? "not set" : "set") +
                ", targetRow is " + ((null==targetRow) ? "not set" : "set") +
                '}';
    }

}
