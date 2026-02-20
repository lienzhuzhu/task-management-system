<template>
  <div class="container mx-auto px-4 py-8 max-w-2xl">
    <div class="mb-6">
      <NuxtLink to="/users" class="text-blue-600 hover:text-blue-800 text-sm">
        ← Back to Users
      </NuxtLink>
      <h1 class="text-3xl font-bold mt-2">Edit User</h1>
    </div>

    <!-- Loading State -->
    <div v-if="loading" class="text-center py-12">
      <div class="inline-block animate-spin rounded-full h-12 w-12 border-4 border-gray-300 border-t-blue-600"></div>
      <p class="mt-4 text-gray-600">Loading user...</p>
    </div>

    <!-- Error Message -->
    <div
      v-else-if="error"
      class="mb-6 bg-red-50 border border-red-200 rounded-lg p-4 text-red-800"
    >
      <p class="font-semibold">Error</p>
      <p class="text-sm">{{ error }}</p>
      <NuxtLink to="/users" class="text-sm underline mt-2 inline-block">
        Go back to users
      </NuxtLink>
    </div>

    <!-- User Form -->
    <form v-else @submit.prevent="handleSubmit" class="bg-white rounded-lg shadow p-6">
      <!-- Name Field -->
      <div class="mb-4">
        <label for="name" class="block text-sm font-medium text-gray-700 mb-1">
          Name <span class="text-red-500">*</span>
        </label>
        <input
          id="name"
          v-model="form.name"
          type="text"
          required
          maxlength="100"
          class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
          :class="{ 'border-red-500': validationErrors.name }"
          placeholder="Enter user name"
        />
        <p v-if="validationErrors.name" class="mt-1 text-sm text-red-600">
          {{ validationErrors.name }}
        </p>
      </div>

      <!-- Email Field -->
      <div class="mb-6">
        <label for="email" class="block text-sm font-medium text-gray-700 mb-1">
          Email <span class="text-red-500">*</span>
        </label>
        <input
          id="email"
          v-model="form.email"
          type="email"
          required
          class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
          :class="{ 'border-red-500': validationErrors.email }"
          placeholder="user@example.com"
        />
        <p v-if="validationErrors.email" class="mt-1 text-sm text-red-600">
          {{ validationErrors.email }}
        </p>
      </div>

      <!-- Form Actions -->
      <div class="flex justify-end space-x-3">
        <NuxtLink
          to="/users"
          class="px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50"
        >
          Cancel
        </NuxtLink>
        <button
          type="submit"
          :disabled="submitting"
          class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          {{ submitting ? 'Updating...' : 'Update User' }}
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
const apiClient = useApiClient()
const router = useRouter()
const route = useRoute()

const userId = route.params.id

// State
const form = ref({
  name: '',
  email: '',
})

const loading = ref(true)
const submitting = ref(false)
const error = ref(null)
const validationErrors = ref({})

// Load user data
async function loadUser() {
  loading.value = true
  error.value = null

  try {
    const user = await apiClient.get(`/users/${userId}`)
    form.value.name = user.name
    form.value.email = user.email
  } catch (err) {
    error.value = err.message || 'Failed to load user'
    console.error('Error loading user:', err)
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
  if (!form.value.name.trim()) {
    validationErrors.value.name = 'Name is required'
    return
  }

  if (form.value.name.length > 100) {
    validationErrors.value.name = 'Name must not exceed 100 characters'
    return
  }

  if (!form.value.email.trim()) {
    validationErrors.value.email = 'Email is required'
    return
  }

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!emailRegex.test(form.value.email)) {
    validationErrors.value.email = 'Please enter a valid email address'
    return
  }

  // Submit to API
  submitting.value = true

  try {
    await apiClient.put(`/users/${userId}`, form.value)
    // Navigate back to users list on success
    router.push('/users')
  } catch (err) {
    // Handle validation errors from backend
    if (err.errors) {
      validationErrors.value = err.errors
    } else {
      error.value = err.message || 'Failed to update user'
    }
    console.error('Error updating user:', err)
  } finally {
    submitting.value = false
  }
}

// Load user on mount
onMounted(() => {
  loadUser()
})
</script>
