<template>
  <dib>
    <b-nav tabs>
      <b-nav-item disabled>Fancy Quiz App</b-nav-item>
      <b-nav-item disabled>Counter: {{ numCorrect }}/{{ numTotal }}</b-nav-item>
    </b-nav>
  </dib>
</template>

<script>
export default {
  props: ["numCorrect", "numTotal"]
};
</script>