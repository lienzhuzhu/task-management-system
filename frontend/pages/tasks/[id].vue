<template>
  <div class="container mx-auto px-4 py-8 max-w-2xl">
    <div class="mb-6">
      <NuxtLink to="/tasks" class="text-blue-600 hover:text-blue-800 text-sm">
        ← Back to Tasks
      </NuxtLink>
      <h1 class="text-3xl font-bold mt-2">Edit Task</h1>
    </div>

    <!-- Loading State -->
    <div v-if="loading" class="text-center py-12">
      <div class="inline-block animate-spin rounded-full h-12 w-12 border-4 border-gray-300 border-t-blue-600"></div>
      <p class="mt-4 text-gray-600">Loading task...</p>
    </div>

    <!-- Error Message -->
    <div v-else-if="error" class="mb-6 bg-red-50 border border-red-200 rounded-lg p-4 text-red-800">
      <p class="font-semibold">Error</p>
      <p class="text-sm">{{ error }}</p>
      <NuxtLink to="/tasks" class="text-sm underline mt-2 inline-block">
        Go back to tasks
      </NuxtLink>
    </div>

    <!-- Task Form -->
    <form v-else @submit.prevent="handleSubmit" class="bg-white rounded-lg shadow p-6">
      <!-- Title Field -->
      <div class="mb-4">
        <label for="title" class="block text-sm font-medium text-gray-700 mb-1">
          Title <span class="text-red-500">*</span>
        </label>
        <input id="title" v-model="form.title" type="text" required maxlength="100"
          class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
          :class="{ 'border-red-500': validationErrors.title }" placeholder="Enter task title" />
        <p v-if="validationErrors.title" class="mt-1 text-sm text-red-600">
          {{ validationErrors.title }}
        </p>
      </div>

      <!-- Form Actions -->
      <div class="flex justify-end space-x-3">
        <NuxtLink to="/tasks" class="px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50">
          Cancel
        </NuxtLink>
        <button type="submit" :disabled="submitting"
          class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed">
          {{ submitting ? 'Updating...' : 'Update Task' }}
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
const apiClient = useApiClient()
const router = useRouter()
const route = useRoute()

const taskId = route.params.id

// State
const form = ref({
  title: '',
  description: '',
})

const loading = ref(true)
const submitting = ref(false)
const error = ref(null)
const validationErrors = ref({})

// Load task data
async function loadTask() {
  loading.value = true
  error.value = null

  try {
    const task = await apiClient.get(`/tasks/${taskId}`)
    form.value.title = task.title
    form.value.description = task.description
  } catch (err) {
    error.value = err.message || 'Failed to load task'
    console.error('Error loading task:', err)
  } finally {
    loading.value = false
  }
}

// Handle form submission
async function handleSubmit() {
  // Reset errors
  error.value = null
  validationErrors.value = {}

  // Client-side validation
  if (!form.value.title.trim()) {
    validationErrors.value.title = 'Title is required'
    return
  }

  if (form.value.title.length > 100) {
    validationErrors.value.title = 'Title must not exceed 100 characters'
    return
  }

  // Submit to API
  submitting.value = true

  try {
    await apiClient.put(`/tasks/${taskId}`, form.value)
    // Navigate back to tasks list on success
    router.push('/tasks')
  } catch (err) {
    // Handle validation errors from backend
    if (err.errors) {
      validationErrors.value = err.errors
    } else {
      error.value = err.message || 'Failed to update task'
    }
    console.error('Error updating task:', err)
  } finally {
    submitting.value = false
  }
}

// Load task on mount
onMounted(() => {
  loadTask()
})
</script>
