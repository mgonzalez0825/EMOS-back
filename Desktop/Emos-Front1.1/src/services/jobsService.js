import axios from "axios";  

const JOBS_API_BASE_URL = "http://localhost:8080/api/v1/jobs";

class JobsService {

    getAllJobs(){
        return axios.get(JOBS_API_BASE_URL);
    }

    getJobById(jobId){
        return axios.get(JOBS_API_BASE_URL + '/' + jobId)
    }

    createJob(job){
        return axios.post(JOBS_API_BASE_URL , job)
    }

    updateJob(job,jobId){
        return axios.put(JOBS_API_BASE_URL + '/' + jobId,job)
    }

    deleteJob(jobId){
        return axios.delete(JOBS_API_BASE_URL + '/' + jobId);
    }
}

export default new JobsService()
