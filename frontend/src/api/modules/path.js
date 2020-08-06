import ApiService from '@/api'

const PathService = {
  get() {
    return ApiService.get('/paths')
  }
}

export default PathService
