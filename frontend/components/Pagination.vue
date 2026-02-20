<template>
  <div class="bg-gray-50 px-6 py-4 border-t border-gray-200">
    <div class="flex flex-col sm:flex-row items-center justify-between gap-4">
      <!-- Results Info -->
      <div class="text-sm text-gray-700">
        Showing
        <span class="font-medium">{{ startItem }}</span>
        to
        <span class="font-medium">{{ endItem }}</span>
        of
        <span class="font-medium">{{ totalElements }}</span>
        results
      </div>

      <!-- Pagination Controls -->
      <div class="flex items-center space-x-2">
        <!-- Previous Button -->
        <button
          @click="goToPage(currentPage - 1)"
          :disabled="currentPage === 0"
          class="px-3 py-1 border border-gray-300 rounded-lg hover:bg-gray-100 disabled:opacity-50 disabled:cursor-not-allowed"
          :class="{ 'cursor-not-allowed': currentPage === 0 }"
        >
          Previous
        </button>

        <!-- Page Numbers -->
        <div class="hidden sm:flex space-x-1">
          <button
            v-for="page in visiblePages"
            :key="page"
            @click="goToPage(page)"
            class="px-3 py-1 border rounded-lg"
            :class="
              page === currentPage
                ? 'bg-blue-600 text-white border-blue-600'
                : 'border-gray-300 hover:bg-gray-100'
            "
          >
            {{ page + 1 }}
          </button>
        </div>

        <!-- Current Page (Mobile) -->
        <div class="sm:hidden text-sm text-gray-700">
          Page {{ currentPage + 1 }} of {{ totalPages }}
        </div>

        <!-- Next Button -->
        <button
          @click="goToPage(currentPage + 1)"
          :disabled="currentPage >= totalPages - 1"
          class="px-3 py-1 border border-gray-300 rounded-lg hover:bg-gray-100 disabled:opacity-50 disabled:cursor-not-allowed"
          :class="{ 'cursor-not-allowed': currentPage >= totalPages - 1 }"
        >
          Next
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
/**
 * Reusable Pagination Component
 *
 * Props:
 * - currentPage: Current page number (0-based)
 * - totalPages: Total number of pages
 * - totalElements: Total number of items
 *
 * Emits:
 * - page-changed: When user clicks on a page number or prev/next
 */

const props = defineProps({
  currentPage: {
    type: Number,
    required: true,
  },
  totalPages: {
    type: Number,
    required: true,
  },
  totalElements: {
    type: Number,
    required: true,
  },
})

const emit = defineEmits(['page-changed'])

// Calculate visible pages (show max 5 page numbers)
const visiblePages = computed(() => {
  const maxVisible = 5
  const pages = []

  let start = Math.max(0, props.currentPage - Math.floor(maxVisible / 2))
  let end = Math.min(props.totalPages, start + maxVisible)

  // Adjust start if we're near the end
  if (end - start < maxVisible) {
    start = Math.max(0, end - maxVisible)
  }

  for (let i = start; i < end; i++) {
    pages.push(i)
  }

  return pages
})

// Calculate item range for display
const startItem = computed(() => {
  return props.currentPage * 10 + 1
})

const endItem = computed(() => {
  return Math.min((props.currentPage + 1) * 10, props.totalElements)
})

// Navigate to a specific page
function goToPage(page) {
  if (page >= 0 && page < props.totalPages) {
    emit('page-changed', page)
  }
}
</script>
