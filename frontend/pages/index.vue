<template>
  <div class="container mx-auto px-4 py-8">
    <div class="mb-8">
      <h1 class="text-3xl font-bold mb-2">Dashboard</h1>
      <p class="text-gray-600">Overview of users and tasks in the system</p>
    </div>

    <!-- Loading State -->
    <div v-if="loading" class="text-center py-12">
      <div class="inline-block animate-spin rounded-full h-12 w-12 border-4 border-gray-300 border-t-blue-600"></div>
      <p class="mt-4 text-gray-600">Loading dashboard...</p>
    </div>

    <!-- Dashboard Cards -->
    <div v-else class="grid grid-cols-1 md:grid-cols-2 gap-6">
      <!-- Total Users Card -->
      <div class="bg-white rounded-lg shadow p-6">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-gray-600 text-sm font-medium">Total Users</p>
            <p class="text-3xl font-bold text-gray-900 mt-2">{{ totalUsers }}</p>
          </div>
          <div class="bg-blue-100 rounded-full p-3">
            <svg
              class="w-8 h-8 text-blue-600"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z"
              />
            </svg>
          </div>
        </div>
        <div class="mt-4">
          <NuxtLink
            to="/users"
            class="text-blue-600 hover:text-blue-800 text-sm font-medium"
          >
            View all users →
          </NuxtLink>
        </div>
      </div>

      <!-- Total Tasks Card -->
      <div class="bg-white rounded-lg shadow p-6">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-gray-600 text-sm font-medium">Total Tasks</p>
            <p class="text-3xl font-bold text-gray-900 mt-2">
              <!-- TODO: Display total tasks count here -->
              <span class="text-gray-400">--</span>
            </p>
          </div>
          <div class="bg-green-100 rounded-full p-3">
            <svg
              class="w-8 h-8 text-green-600"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-6 9l2 2 4-4"
              />
            </svg>
          </div>
        </div>
        <div class="mt-4">
          <NuxtLink
            to="/tasks"
            class="text-green-600 hover:text-green-800 text-sm font-medium"
          >
            View all tasks →
          </NuxtLink>
        </div>
      </div>
    </div>

    <!-- Quick Links -->
    <div class="mt-8">
      <h2 class="text-xl font-semibold mb-4">Quick Actions</h2>
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <NuxtLink
          to="/users/create"
          class="bg-white rounded-lg shadow p-4 hover:shadow-md transition flex items-center space-x-3"
        >
          <div class="bg-blue-100 rounded-full p-2">
            <svg
              class="w-6 h-6 text-blue-600"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M12 4v16m8-8H4"
              />
            </svg>
          </div>
          <span class="font-medium text-gray-900">Create New User</span>
        </NuxtLink>

        <NuxtLink
          to="/tasks"
          class="bg-white rounded-lg shadow p-4 hover:shadow-md transition flex items-center space-x-3"
        >
          <div class="bg-green-100 rounded-full p-2">
            <svg
              class="w-6 h-6 text-green-600"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M12 4v16m8-8H4"
              />
            </svg>
          </div>
          <span class="font-medium text-gray-900">Create New Task</span>
        </NuxtLink>
      </div>
    </div>
  </div>
</template>

<script setup>
const apiClient = useApiClient()

// State
const loading = ref(true)
const totalUsers = ref(0)
// TODO: Add totalTasks state variable

// Fetch dashboard data
async function fetchDashboardData() {
  loading.value = true

  try {
    // Fetch total users count
    totalUsers.value = await apiClient.get('/users/count')

    // TODO: Fetch total tasks count
    // Call: apiClient.get('/tasks/count')
    // Store in totalTasks.value
    // Reference: How totalUsers is fetched above
  } catch (err) {
    console.error('Error fetching dashboard data:', err)
  } finally {
    loading.value = false
  }
}

// Load dashboard data on mount
onMounted(() => {
  fetchDashboardData()
})
</script>
