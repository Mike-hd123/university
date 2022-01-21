<template>
  <view>
    <uni-forms :modelValue="formData">
      <uni-forms-item label="姓名" name="username">
        <uni-easyinput
          type="text"
          v-model="formData.username"
          placeholder="请输入姓名"
        />
      </uni-forms-item>
      <uni-forms-item label="性别" name="ssex">
        <uni-data-checkbox
          :multiple="false"
          v-model="formData.ssex"
          :localdata="sex"
        />
      </uni-forms-item>
      <uni-forms-item label="年龄" name="sage">
        <uni-easyinput
          type="number"
          v-model="formData.sage"
          placeholder="请输入年龄"
        />
      </uni-forms-item>
      <uni-forms-item label="班级" name="classname">
        <uni-easyinput
          type="text"
          v-model="formData.classname"
          placeholder="请输入班级"
        />
      </uni-forms-item>
      <uni-forms-item label="学部" name="sdept">
        <uni-easyinput
          type="text"
          v-model="formData.sdept"
          placeholder="请输入学部"
        />
      </uni-forms-item>
    </uni-forms>
  </view>
</template>

<script>
export default {
  name: "studentinfo",
  props: { formData: {} },
  data() {
    return {
      sex: [
        { value: "男", text: "男" },
        { value: "女", text: "女" },
      ],
    };
  },
};
</script>
