package com.eg.chatserver.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FileExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andFileIdIsNull() {
            addCriterion("file_id is null");
            return (Criteria) this;
        }

        public Criteria andFileIdIsNotNull() {
            addCriterion("file_id is not null");
            return (Criteria) this;
        }

        public Criteria andFileIdEqualTo(String value) {
            addCriterion("file_id =", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotEqualTo(String value) {
            addCriterion("file_id <>", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdGreaterThan(String value) {
            addCriterion("file_id >", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdGreaterThanOrEqualTo(String value) {
            addCriterion("file_id >=", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLessThan(String value) {
            addCriterion("file_id <", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLessThanOrEqualTo(String value) {
            addCriterion("file_id <=", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLike(String value) {
            addCriterion("file_id like", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotLike(String value) {
            addCriterion("file_id not like", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdIn(List<String> values) {
            addCriterion("file_id in", values, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotIn(List<String> values) {
            addCriterion("file_id not in", values, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdBetween(String value1, String value2) {
            addCriterion("file_id between", value1, value2, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotBetween(String value1, String value2) {
            addCriterion("file_id not between", value1, value2, "fileId");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andSizeIsNull() {
            addCriterion("size is null");
            return (Criteria) this;
        }

        public Criteria andSizeIsNotNull() {
            addCriterion("size is not null");
            return (Criteria) this;
        }

        public Criteria andSizeEqualTo(Long value) {
            addCriterion("size =", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotEqualTo(Long value) {
            addCriterion("size <>", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeGreaterThan(Long value) {
            addCriterion("size >", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeGreaterThanOrEqualTo(Long value) {
            addCriterion("size >=", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeLessThan(Long value) {
            addCriterion("size <", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeLessThanOrEqualTo(Long value) {
            addCriterion("size <=", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeIn(List<Long> values) {
            addCriterion("size in", values, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotIn(List<Long> values) {
            addCriterion("size not in", values, "size");
            return (Criteria) this;
        }

        public Criteria andSizeBetween(Long value1, Long value2) {
            addCriterion("size between", value1, value2, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotBetween(Long value1, Long value2) {
            addCriterion("size not between", value1, value2, "size");
            return (Criteria) this;
        }

        public Criteria andOriginalNameIsNull() {
            addCriterion("original_name is null");
            return (Criteria) this;
        }

        public Criteria andOriginalNameIsNotNull() {
            addCriterion("original_name is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalNameEqualTo(String value) {
            addCriterion("original_name =", value, "originalName");
            return (Criteria) this;
        }

        public Criteria andOriginalNameNotEqualTo(String value) {
            addCriterion("original_name <>", value, "originalName");
            return (Criteria) this;
        }

        public Criteria andOriginalNameGreaterThan(String value) {
            addCriterion("original_name >", value, "originalName");
            return (Criteria) this;
        }

        public Criteria andOriginalNameGreaterThanOrEqualTo(String value) {
            addCriterion("original_name >=", value, "originalName");
            return (Criteria) this;
        }

        public Criteria andOriginalNameLessThan(String value) {
            addCriterion("original_name <", value, "originalName");
            return (Criteria) this;
        }

        public Criteria andOriginalNameLessThanOrEqualTo(String value) {
            addCriterion("original_name <=", value, "originalName");
            return (Criteria) this;
        }

        public Criteria andOriginalNameLike(String value) {
            addCriterion("original_name like", value, "originalName");
            return (Criteria) this;
        }

        public Criteria andOriginalNameNotLike(String value) {
            addCriterion("original_name not like", value, "originalName");
            return (Criteria) this;
        }

        public Criteria andOriginalNameIn(List<String> values) {
            addCriterion("original_name in", values, "originalName");
            return (Criteria) this;
        }

        public Criteria andOriginalNameNotIn(List<String> values) {
            addCriterion("original_name not in", values, "originalName");
            return (Criteria) this;
        }

        public Criteria andOriginalNameBetween(String value1, String value2) {
            addCriterion("original_name between", value1, value2, "originalName");
            return (Criteria) this;
        }

        public Criteria andOriginalNameNotBetween(String value1, String value2) {
            addCriterion("original_name not between", value1, value2, "originalName");
            return (Criteria) this;
        }

        public Criteria andExtensionIsNull() {
            addCriterion("extension is null");
            return (Criteria) this;
        }

        public Criteria andExtensionIsNotNull() {
            addCriterion("extension is not null");
            return (Criteria) this;
        }

        public Criteria andExtensionEqualTo(String value) {
            addCriterion("extension =", value, "extension");
            return (Criteria) this;
        }

        public Criteria andExtensionNotEqualTo(String value) {
            addCriterion("extension <>", value, "extension");
            return (Criteria) this;
        }

        public Criteria andExtensionGreaterThan(String value) {
            addCriterion("extension >", value, "extension");
            return (Criteria) this;
        }

        public Criteria andExtensionGreaterThanOrEqualTo(String value) {
            addCriterion("extension >=", value, "extension");
            return (Criteria) this;
        }

        public Criteria andExtensionLessThan(String value) {
            addCriterion("extension <", value, "extension");
            return (Criteria) this;
        }

        public Criteria andExtensionLessThanOrEqualTo(String value) {
            addCriterion("extension <=", value, "extension");
            return (Criteria) this;
        }

        public Criteria andExtensionLike(String value) {
            addCriterion("extension like", value, "extension");
            return (Criteria) this;
        }

        public Criteria andExtensionNotLike(String value) {
            addCriterion("extension not like", value, "extension");
            return (Criteria) this;
        }

        public Criteria andExtensionIn(List<String> values) {
            addCriterion("extension in", values, "extension");
            return (Criteria) this;
        }

        public Criteria andExtensionNotIn(List<String> values) {
            addCriterion("extension not in", values, "extension");
            return (Criteria) this;
        }

        public Criteria andExtensionBetween(String value1, String value2) {
            addCriterion("extension between", value1, value2, "extension");
            return (Criteria) this;
        }

        public Criteria andExtensionNotBetween(String value1, String value2) {
            addCriterion("extension not between", value1, value2, "extension");
            return (Criteria) this;
        }

        public Criteria andBucketNameIsNull() {
            addCriterion("bucket_name is null");
            return (Criteria) this;
        }

        public Criteria andBucketNameIsNotNull() {
            addCriterion("bucket_name is not null");
            return (Criteria) this;
        }

        public Criteria andBucketNameEqualTo(String value) {
            addCriterion("bucket_name =", value, "bucketName");
            return (Criteria) this;
        }

        public Criteria andBucketNameNotEqualTo(String value) {
            addCriterion("bucket_name <>", value, "bucketName");
            return (Criteria) this;
        }

        public Criteria andBucketNameGreaterThan(String value) {
            addCriterion("bucket_name >", value, "bucketName");
            return (Criteria) this;
        }

        public Criteria andBucketNameGreaterThanOrEqualTo(String value) {
            addCriterion("bucket_name >=", value, "bucketName");
            return (Criteria) this;
        }

        public Criteria andBucketNameLessThan(String value) {
            addCriterion("bucket_name <", value, "bucketName");
            return (Criteria) this;
        }

        public Criteria andBucketNameLessThanOrEqualTo(String value) {
            addCriterion("bucket_name <=", value, "bucketName");
            return (Criteria) this;
        }

        public Criteria andBucketNameLike(String value) {
            addCriterion("bucket_name like", value, "bucketName");
            return (Criteria) this;
        }

        public Criteria andBucketNameNotLike(String value) {
            addCriterion("bucket_name not like", value, "bucketName");
            return (Criteria) this;
        }

        public Criteria andBucketNameIn(List<String> values) {
            addCriterion("bucket_name in", values, "bucketName");
            return (Criteria) this;
        }

        public Criteria andBucketNameNotIn(List<String> values) {
            addCriterion("bucket_name not in", values, "bucketName");
            return (Criteria) this;
        }

        public Criteria andBucketNameBetween(String value1, String value2) {
            addCriterion("bucket_name between", value1, value2, "bucketName");
            return (Criteria) this;
        }

        public Criteria andBucketNameNotBetween(String value1, String value2) {
            addCriterion("bucket_name not between", value1, value2, "bucketName");
            return (Criteria) this;
        }

        public Criteria andObjectNameIsNull() {
            addCriterion("object_name is null");
            return (Criteria) this;
        }

        public Criteria andObjectNameIsNotNull() {
            addCriterion("object_name is not null");
            return (Criteria) this;
        }

        public Criteria andObjectNameEqualTo(String value) {
            addCriterion("object_name =", value, "objectName");
            return (Criteria) this;
        }

        public Criteria andObjectNameNotEqualTo(String value) {
            addCriterion("object_name <>", value, "objectName");
            return (Criteria) this;
        }

        public Criteria andObjectNameGreaterThan(String value) {
            addCriterion("object_name >", value, "objectName");
            return (Criteria) this;
        }

        public Criteria andObjectNameGreaterThanOrEqualTo(String value) {
            addCriterion("object_name >=", value, "objectName");
            return (Criteria) this;
        }

        public Criteria andObjectNameLessThan(String value) {
            addCriterion("object_name <", value, "objectName");
            return (Criteria) this;
        }

        public Criteria andObjectNameLessThanOrEqualTo(String value) {
            addCriterion("object_name <=", value, "objectName");
            return (Criteria) this;
        }

        public Criteria andObjectNameLike(String value) {
            addCriterion("object_name like", value, "objectName");
            return (Criteria) this;
        }

        public Criteria andObjectNameNotLike(String value) {
            addCriterion("object_name not like", value, "objectName");
            return (Criteria) this;
        }

        public Criteria andObjectNameIn(List<String> values) {
            addCriterion("object_name in", values, "objectName");
            return (Criteria) this;
        }

        public Criteria andObjectNameNotIn(List<String> values) {
            addCriterion("object_name not in", values, "objectName");
            return (Criteria) this;
        }

        public Criteria andObjectNameBetween(String value1, String value2) {
            addCriterion("object_name between", value1, value2, "objectName");
            return (Criteria) this;
        }

        public Criteria andObjectNameNotBetween(String value1, String value2) {
            addCriterion("object_name not between", value1, value2, "objectName");
            return (Criteria) this;
        }

        public Criteria andImageWidthIsNull() {
            addCriterion("image_width is null");
            return (Criteria) this;
        }

        public Criteria andImageWidthIsNotNull() {
            addCriterion("image_width is not null");
            return (Criteria) this;
        }

        public Criteria andImageWidthEqualTo(Integer value) {
            addCriterion("image_width =", value, "imageWidth");
            return (Criteria) this;
        }

        public Criteria andImageWidthNotEqualTo(Integer value) {
            addCriterion("image_width <>", value, "imageWidth");
            return (Criteria) this;
        }

        public Criteria andImageWidthGreaterThan(Integer value) {
            addCriterion("image_width >", value, "imageWidth");
            return (Criteria) this;
        }

        public Criteria andImageWidthGreaterThanOrEqualTo(Integer value) {
            addCriterion("image_width >=", value, "imageWidth");
            return (Criteria) this;
        }

        public Criteria andImageWidthLessThan(Integer value) {
            addCriterion("image_width <", value, "imageWidth");
            return (Criteria) this;
        }

        public Criteria andImageWidthLessThanOrEqualTo(Integer value) {
            addCriterion("image_width <=", value, "imageWidth");
            return (Criteria) this;
        }

        public Criteria andImageWidthIn(List<Integer> values) {
            addCriterion("image_width in", values, "imageWidth");
            return (Criteria) this;
        }

        public Criteria andImageWidthNotIn(List<Integer> values) {
            addCriterion("image_width not in", values, "imageWidth");
            return (Criteria) this;
        }

        public Criteria andImageWidthBetween(Integer value1, Integer value2) {
            addCriterion("image_width between", value1, value2, "imageWidth");
            return (Criteria) this;
        }

        public Criteria andImageWidthNotBetween(Integer value1, Integer value2) {
            addCriterion("image_width not between", value1, value2, "imageWidth");
            return (Criteria) this;
        }

        public Criteria andImageHeightIsNull() {
            addCriterion("image_height is null");
            return (Criteria) this;
        }

        public Criteria andImageHeightIsNotNull() {
            addCriterion("image_height is not null");
            return (Criteria) this;
        }

        public Criteria andImageHeightEqualTo(Integer value) {
            addCriterion("image_height =", value, "imageHeight");
            return (Criteria) this;
        }

        public Criteria andImageHeightNotEqualTo(Integer value) {
            addCriterion("image_height <>", value, "imageHeight");
            return (Criteria) this;
        }

        public Criteria andImageHeightGreaterThan(Integer value) {
            addCriterion("image_height >", value, "imageHeight");
            return (Criteria) this;
        }

        public Criteria andImageHeightGreaterThanOrEqualTo(Integer value) {
            addCriterion("image_height >=", value, "imageHeight");
            return (Criteria) this;
        }

        public Criteria andImageHeightLessThan(Integer value) {
            addCriterion("image_height <", value, "imageHeight");
            return (Criteria) this;
        }

        public Criteria andImageHeightLessThanOrEqualTo(Integer value) {
            addCriterion("image_height <=", value, "imageHeight");
            return (Criteria) this;
        }

        public Criteria andImageHeightIn(List<Integer> values) {
            addCriterion("image_height in", values, "imageHeight");
            return (Criteria) this;
        }

        public Criteria andImageHeightNotIn(List<Integer> values) {
            addCriterion("image_height not in", values, "imageHeight");
            return (Criteria) this;
        }

        public Criteria andImageHeightBetween(Integer value1, Integer value2) {
            addCriterion("image_height between", value1, value2, "imageHeight");
            return (Criteria) this;
        }

        public Criteria andImageHeightNotBetween(Integer value1, Integer value2) {
            addCriterion("image_height not between", value1, value2, "imageHeight");
            return (Criteria) this;
        }

        public Criteria andImageFormatIsNull() {
            addCriterion("image_format is null");
            return (Criteria) this;
        }

        public Criteria andImageFormatIsNotNull() {
            addCriterion("image_format is not null");
            return (Criteria) this;
        }

        public Criteria andImageFormatEqualTo(String value) {
            addCriterion("image_format =", value, "imageFormat");
            return (Criteria) this;
        }

        public Criteria andImageFormatNotEqualTo(String value) {
            addCriterion("image_format <>", value, "imageFormat");
            return (Criteria) this;
        }

        public Criteria andImageFormatGreaterThan(String value) {
            addCriterion("image_format >", value, "imageFormat");
            return (Criteria) this;
        }

        public Criteria andImageFormatGreaterThanOrEqualTo(String value) {
            addCriterion("image_format >=", value, "imageFormat");
            return (Criteria) this;
        }

        public Criteria andImageFormatLessThan(String value) {
            addCriterion("image_format <", value, "imageFormat");
            return (Criteria) this;
        }

        public Criteria andImageFormatLessThanOrEqualTo(String value) {
            addCriterion("image_format <=", value, "imageFormat");
            return (Criteria) this;
        }

        public Criteria andImageFormatLike(String value) {
            addCriterion("image_format like", value, "imageFormat");
            return (Criteria) this;
        }

        public Criteria andImageFormatNotLike(String value) {
            addCriterion("image_format not like", value, "imageFormat");
            return (Criteria) this;
        }

        public Criteria andImageFormatIn(List<String> values) {
            addCriterion("image_format in", values, "imageFormat");
            return (Criteria) this;
        }

        public Criteria andImageFormatNotIn(List<String> values) {
            addCriterion("image_format not in", values, "imageFormat");
            return (Criteria) this;
        }

        public Criteria andImageFormatBetween(String value1, String value2) {
            addCriterion("image_format between", value1, value2, "imageFormat");
            return (Criteria) this;
        }

        public Criteria andImageFormatNotBetween(String value1, String value2) {
            addCriterion("image_format not between", value1, value2, "imageFormat");
            return (Criteria) this;
        }

        public Criteria andMimeTypeIsNull() {
            addCriterion("mime_type is null");
            return (Criteria) this;
        }

        public Criteria andMimeTypeIsNotNull() {
            addCriterion("mime_type is not null");
            return (Criteria) this;
        }

        public Criteria andMimeTypeEqualTo(String value) {
            addCriterion("mime_type =", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeNotEqualTo(String value) {
            addCriterion("mime_type <>", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeGreaterThan(String value) {
            addCriterion("mime_type >", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("mime_type >=", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeLessThan(String value) {
            addCriterion("mime_type <", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeLessThanOrEqualTo(String value) {
            addCriterion("mime_type <=", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeLike(String value) {
            addCriterion("mime_type like", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeNotLike(String value) {
            addCriterion("mime_type not like", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeIn(List<String> values) {
            addCriterion("mime_type in", values, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeNotIn(List<String> values) {
            addCriterion("mime_type not in", values, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeBetween(String value1, String value2) {
            addCriterion("mime_type between", value1, value2, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeNotBetween(String value1, String value2) {
            addCriterion("mime_type not between", value1, value2, "mimeType");
            return (Criteria) this;
        }

        public Criteria andEtagIsNull() {
            addCriterion("etag is null");
            return (Criteria) this;
        }

        public Criteria andEtagIsNotNull() {
            addCriterion("etag is not null");
            return (Criteria) this;
        }

        public Criteria andEtagEqualTo(String value) {
            addCriterion("etag =", value, "etag");
            return (Criteria) this;
        }

        public Criteria andEtagNotEqualTo(String value) {
            addCriterion("etag <>", value, "etag");
            return (Criteria) this;
        }

        public Criteria andEtagGreaterThan(String value) {
            addCriterion("etag >", value, "etag");
            return (Criteria) this;
        }

        public Criteria andEtagGreaterThanOrEqualTo(String value) {
            addCriterion("etag >=", value, "etag");
            return (Criteria) this;
        }

        public Criteria andEtagLessThan(String value) {
            addCriterion("etag <", value, "etag");
            return (Criteria) this;
        }

        public Criteria andEtagLessThanOrEqualTo(String value) {
            addCriterion("etag <=", value, "etag");
            return (Criteria) this;
        }

        public Criteria andEtagLike(String value) {
            addCriterion("etag like", value, "etag");
            return (Criteria) this;
        }

        public Criteria andEtagNotLike(String value) {
            addCriterion("etag not like", value, "etag");
            return (Criteria) this;
        }

        public Criteria andEtagIn(List<String> values) {
            addCriterion("etag in", values, "etag");
            return (Criteria) this;
        }

        public Criteria andEtagNotIn(List<String> values) {
            addCriterion("etag not in", values, "etag");
            return (Criteria) this;
        }

        public Criteria andEtagBetween(String value1, String value2) {
            addCriterion("etag between", value1, value2, "etag");
            return (Criteria) this;
        }

        public Criteria andEtagNotBetween(String value1, String value2) {
            addCriterion("etag not between", value1, value2, "etag");
            return (Criteria) this;
        }

        public Criteria andOssUrlIsNull() {
            addCriterion("oss_url is null");
            return (Criteria) this;
        }

        public Criteria andOssUrlIsNotNull() {
            addCriterion("oss_url is not null");
            return (Criteria) this;
        }

        public Criteria andOssUrlEqualTo(String value) {
            addCriterion("oss_url =", value, "ossUrl");
            return (Criteria) this;
        }

        public Criteria andOssUrlNotEqualTo(String value) {
            addCriterion("oss_url <>", value, "ossUrl");
            return (Criteria) this;
        }

        public Criteria andOssUrlGreaterThan(String value) {
            addCriterion("oss_url >", value, "ossUrl");
            return (Criteria) this;
        }

        public Criteria andOssUrlGreaterThanOrEqualTo(String value) {
            addCriterion("oss_url >=", value, "ossUrl");
            return (Criteria) this;
        }

        public Criteria andOssUrlLessThan(String value) {
            addCriterion("oss_url <", value, "ossUrl");
            return (Criteria) this;
        }

        public Criteria andOssUrlLessThanOrEqualTo(String value) {
            addCriterion("oss_url <=", value, "ossUrl");
            return (Criteria) this;
        }

        public Criteria andOssUrlLike(String value) {
            addCriterion("oss_url like", value, "ossUrl");
            return (Criteria) this;
        }

        public Criteria andOssUrlNotLike(String value) {
            addCriterion("oss_url not like", value, "ossUrl");
            return (Criteria) this;
        }

        public Criteria andOssUrlIn(List<String> values) {
            addCriterion("oss_url in", values, "ossUrl");
            return (Criteria) this;
        }

        public Criteria andOssUrlNotIn(List<String> values) {
            addCriterion("oss_url not in", values, "ossUrl");
            return (Criteria) this;
        }

        public Criteria andOssUrlBetween(String value1, String value2) {
            addCriterion("oss_url between", value1, value2, "ossUrl");
            return (Criteria) this;
        }

        public Criteria andOssUrlNotBetween(String value1, String value2) {
            addCriterion("oss_url not between", value1, value2, "ossUrl");
            return (Criteria) this;
        }

        public Criteria andCdnUrlIsNull() {
            addCriterion("cdn_url is null");
            return (Criteria) this;
        }

        public Criteria andCdnUrlIsNotNull() {
            addCriterion("cdn_url is not null");
            return (Criteria) this;
        }

        public Criteria andCdnUrlEqualTo(String value) {
            addCriterion("cdn_url =", value, "cdnUrl");
            return (Criteria) this;
        }

        public Criteria andCdnUrlNotEqualTo(String value) {
            addCriterion("cdn_url <>", value, "cdnUrl");
            return (Criteria) this;
        }

        public Criteria andCdnUrlGreaterThan(String value) {
            addCriterion("cdn_url >", value, "cdnUrl");
            return (Criteria) this;
        }

        public Criteria andCdnUrlGreaterThanOrEqualTo(String value) {
            addCriterion("cdn_url >=", value, "cdnUrl");
            return (Criteria) this;
        }

        public Criteria andCdnUrlLessThan(String value) {
            addCriterion("cdn_url <", value, "cdnUrl");
            return (Criteria) this;
        }

        public Criteria andCdnUrlLessThanOrEqualTo(String value) {
            addCriterion("cdn_url <=", value, "cdnUrl");
            return (Criteria) this;
        }

        public Criteria andCdnUrlLike(String value) {
            addCriterion("cdn_url like", value, "cdnUrl");
            return (Criteria) this;
        }

        public Criteria andCdnUrlNotLike(String value) {
            addCriterion("cdn_url not like", value, "cdnUrl");
            return (Criteria) this;
        }

        public Criteria andCdnUrlIn(List<String> values) {
            addCriterion("cdn_url in", values, "cdnUrl");
            return (Criteria) this;
        }

        public Criteria andCdnUrlNotIn(List<String> values) {
            addCriterion("cdn_url not in", values, "cdnUrl");
            return (Criteria) this;
        }

        public Criteria andCdnUrlBetween(String value1, String value2) {
            addCriterion("cdn_url between", value1, value2, "cdnUrl");
            return (Criteria) this;
        }

        public Criteria andCdnUrlNotBetween(String value1, String value2) {
            addCriterion("cdn_url not between", value1, value2, "cdnUrl");
            return (Criteria) this;
        }

        public Criteria andImagePreviewUrlIsNull() {
            addCriterion("image_preview_url is null");
            return (Criteria) this;
        }

        public Criteria andImagePreviewUrlIsNotNull() {
            addCriterion("image_preview_url is not null");
            return (Criteria) this;
        }

        public Criteria andImagePreviewUrlEqualTo(String value) {
            addCriterion("image_preview_url =", value, "imagePreviewUrl");
            return (Criteria) this;
        }

        public Criteria andImagePreviewUrlNotEqualTo(String value) {
            addCriterion("image_preview_url <>", value, "imagePreviewUrl");
            return (Criteria) this;
        }

        public Criteria andImagePreviewUrlGreaterThan(String value) {
            addCriterion("image_preview_url >", value, "imagePreviewUrl");
            return (Criteria) this;
        }

        public Criteria andImagePreviewUrlGreaterThanOrEqualTo(String value) {
            addCriterion("image_preview_url >=", value, "imagePreviewUrl");
            return (Criteria) this;
        }

        public Criteria andImagePreviewUrlLessThan(String value) {
            addCriterion("image_preview_url <", value, "imagePreviewUrl");
            return (Criteria) this;
        }

        public Criteria andImagePreviewUrlLessThanOrEqualTo(String value) {
            addCriterion("image_preview_url <=", value, "imagePreviewUrl");
            return (Criteria) this;
        }

        public Criteria andImagePreviewUrlLike(String value) {
            addCriterion("image_preview_url like", value, "imagePreviewUrl");
            return (Criteria) this;
        }

        public Criteria andImagePreviewUrlNotLike(String value) {
            addCriterion("image_preview_url not like", value, "imagePreviewUrl");
            return (Criteria) this;
        }

        public Criteria andImagePreviewUrlIn(List<String> values) {
            addCriterion("image_preview_url in", values, "imagePreviewUrl");
            return (Criteria) this;
        }

        public Criteria andImagePreviewUrlNotIn(List<String> values) {
            addCriterion("image_preview_url not in", values, "imagePreviewUrl");
            return (Criteria) this;
        }

        public Criteria andImagePreviewUrlBetween(String value1, String value2) {
            addCriterion("image_preview_url between", value1, value2, "imagePreviewUrl");
            return (Criteria) this;
        }

        public Criteria andImagePreviewUrlNotBetween(String value1, String value2) {
            addCriterion("image_preview_url not between", value1, value2, "imagePreviewUrl");
            return (Criteria) this;
        }

        public Criteria andAudioDurationIsNull() {
            addCriterion("audio_duration is null");
            return (Criteria) this;
        }

        public Criteria andAudioDurationIsNotNull() {
            addCriterion("audio_duration is not null");
            return (Criteria) this;
        }

        public Criteria andAudioDurationEqualTo(Long value) {
            addCriterion("audio_duration =", value, "audioDuration");
            return (Criteria) this;
        }

        public Criteria andAudioDurationNotEqualTo(Long value) {
            addCriterion("audio_duration <>", value, "audioDuration");
            return (Criteria) this;
        }

        public Criteria andAudioDurationGreaterThan(Long value) {
            addCriterion("audio_duration >", value, "audioDuration");
            return (Criteria) this;
        }

        public Criteria andAudioDurationGreaterThanOrEqualTo(Long value) {
            addCriterion("audio_duration >=", value, "audioDuration");
            return (Criteria) this;
        }

        public Criteria andAudioDurationLessThan(Long value) {
            addCriterion("audio_duration <", value, "audioDuration");
            return (Criteria) this;
        }

        public Criteria andAudioDurationLessThanOrEqualTo(Long value) {
            addCriterion("audio_duration <=", value, "audioDuration");
            return (Criteria) this;
        }

        public Criteria andAudioDurationIn(List<Long> values) {
            addCriterion("audio_duration in", values, "audioDuration");
            return (Criteria) this;
        }

        public Criteria andAudioDurationNotIn(List<Long> values) {
            addCriterion("audio_duration not in", values, "audioDuration");
            return (Criteria) this;
        }

        public Criteria andAudioDurationBetween(Long value1, Long value2) {
            addCriterion("audio_duration between", value1, value2, "audioDuration");
            return (Criteria) this;
        }

        public Criteria andAudioDurationNotBetween(Long value1, Long value2) {
            addCriterion("audio_duration not between", value1, value2, "audioDuration");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}