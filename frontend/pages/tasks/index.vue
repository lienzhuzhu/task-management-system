<template>
  <div class="container mx-auto px-4 py-8">
    <div class="mb-6">
      <h1 class="text-3xl font-bold mb-2">Tasks</h1>
      <p class="text-gray-600">Manage tasks in the system</p>
    </div>

    <!-- Actions Bar -->
    <div class="mb-6 flex flex-col md:flex-row gap-4 justify-between items-start md:items-center">
      <!-- Filter -->
      <div class="w-full md:w-64">
        <label for="nameFilter" class="block text-sm font-medium text-gray-700 mb-1">
          Filter by status
        </label>
        <input
          id="statusFilter"
          v-model="statusFilter"
          type="text"
          placeholder="Search tasks by status..."
          class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
          @input="handleFilterChange"
        />
      </div>

      <!-- Create Button -->
      <NuxtLink
        to="/tasks/create"
        class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition"
      >
        + Create Task
      </NuxtLink>
    </div>

    <!-- Loading State -->
    <div v-if="loading" class="text-center py-12">
      <div class="inline-block animate-spin rounded-full h-12 w-12 border-4 border-gray-300 border-t-blue-600"></div>
      <p class="mt-4 text-gray-600">Loading tasks...</p>
    </div>

    <!-- Error State -->
    <div v-else-if="error" class="bg-red-50 border border-red-200 rounded-lg p-4 text-red-800">
      <p class="font-semibold">Error loading tasks</p>
      <p class="text-sm">{{ error }}</p>
    </div>

    <!-- Tasks Table -->
    <div v-else class="bg-white rounded-lg shadow overflow-hidden">
      <div v-if="tasks.length === 0" class="text-center py-12 text-gray-500">
        No tasks found{{ statusFilter ? ' matching your filter' : '' }}.
      </div>

      <table v-else class="min-w-full divide-y divide-gray-200">
        <thead class="bg-gray-50">
          <tr>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              ID
            </th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Name
            </th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Email
            </th>
            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Created At
            </th>
            <th class="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
              Actions
            </th>
          </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
          <tr v-for="task in tasks" :key="task.id" class="hover:bg-gray-50">
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
              {{ task.id }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
              {{ task.name }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
              {{ task.email }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
              {{ formatDate(task.createdAt) }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium space-x-2">
              <NuxtLink
                :to="`/tasks/${task.id}`"
                class="text-blue-600 hover:text-blue-900"
              >
                Edit
              </NuxtLink>
              <button
                @click="confirmDelete(task)"
                class="text-red-600 hover:text-red-900"
              >
                Delete
              </button>
            </td>
          </tr>
        </tbody>
      </table>

      <!-- Pagination -->
      <Pagination
        v-if="totalPages > 1"
        :current-page="currentPage"
        :total-pages="totalPages"
        :total-elements="totalElements"
        @page-changed="handlePageChange"
      />
    </div>

    <!-- Delete Confirmation Modal -->
    <div
      v-if="taskToDelete"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
      @click.self="taskToDelete = null"
    >
      <div class="bg-white rounded-lg p-6 max-w-md w-full mx-4">
        <h3 class="text-lg font-semibold mb-4">Confirm Delete</h3>
        <p class="text-gray-600 mb-6">
          Are you sure you want to delete task <strong>{{ taskToDelete.name }}</strong>?
          This action cannot be undone.
        </p>
        <div class="flex justify-end space-x-3">
          <button
            @click="taskToDelete = null"
            class="px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50"
          >
            Cancel
          </button>
          <button
            @click="deleteTask"
            :disabled="deleting"
            class="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 disabled:opacity-50"
          >
            {{ deleting ? 'Deleting...' : 'Delete' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const apiClient = useApiClient()

// State
const tasks = ref([])
const loading = ref(true)
const error = ref(null)
const nameFilter = ref('')
const currentPage = ref(0)
const totalPages = ref(0)
const totalElements = ref(0)
const pageSize = 10

// Delete modal state
const taskToDelete = ref(null)
const deleting = ref(false)

// Fetch tasks from API
async function fetchTasks() {
  loading.value = true
  error.value = null

  try {
    const params = new URLSearchParams({
      page: currentPage.value.toString(),
      size: pageSize.toString(),
    })

    // Add name filter if provided
    if (nameFilter.value.trim()) {
      params.append('name', nameFilter.value.trim())
    }

    const data = await apiClient.get(`/tasks?${params.toString()}`)

    tasks.value = data.content
    totalPages.value = data.page.totalPages
    totalElements.value = data.page.totalElements
  } catch (err) {
    error.value = err.message || 'Failed to load tasks'
    console.error('Error fetching tasks:', err)
  } finally {
    loading.value = false
  }
}

// Handle filter change (debounced)
let filterTimeout = null
function handleFilterChange() {
  clearTimeout(filterTimeout)
  filterTimeout = setTimeout(() => {
    currentPage.value = 0 // Reset to first page when filter changes
    fetchTasks()
  }, 300) // 300ms debounce
}

// Handle page change
function handlePageChange(page) {
  currentPage.value = page
  fetchTasks()
}

// Confirm delete
function confirmDelete(task) {
  taskToDelete.value = task
}

// Delete task
async function deleteTask() {
  if (!taskToDelete.value) return

  deleting.value = true

  try {
    await apiClient.delete(`/tasks/${taskToDelete.value.id}`)
    taskToDelete.value = null

    // Refresh the list
    await fetchTasks()
  } catch (err) {
    alert('Failed to delete task: ' + (err.message || 'Unknown error'))
    console.error('Error deleting task:', err)
  } finally {
    deleting.value = false
  }
}

// Format date for display
function formatDate(dateString) {
  if (!dateString) return 'N/A'
  const date = new Date(dateString)
  return date.toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
  })
}

// Load tasks on mount
onMounted(() => {
  fetchTasks()
})
</script>
