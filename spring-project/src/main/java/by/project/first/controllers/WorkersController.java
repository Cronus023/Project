package by.project.first.controllers;

import by.project.first.controllers.ReqAndRes.AddWorkerRequest;
import by.project.first.controllers.ReqAndRes.DeleteWorkerRequest;
import by.project.first.models.Message;
import by.project.first.models.TrainingModel;
import by.project.first.models.WorkerModel;
import by.project.first.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class WorkersController {

    private final WorkerService workerService;

    @Autowired
    public WorkersController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping("/workers/getWorkersInOffice")
    public Set<WorkerModel> getWorkersInOffice(@RequestParam String name) {
        return workerService.getWorkersInOffice(name);
    }

    @GetMapping("/workers/viewTrainings")
    public Iterable<TrainingModel> viewTrainings(@RequestParam Long id) {
        return workerService.viewTrainings(id);
    }

    @GetMapping("/workers/getWorkerById")
    public WorkerModel getWorkerById(@RequestParam Long id) {
        return workerService.getWorkerById(id);
    }

    @PostMapping("/workers/deleteWorker")
    public Message deleteWorker(@RequestBody DeleteWorkerRequest request) {
        return workerService.deleteWorker(request);
    }

    @PostMapping("/workers/addWorker")
    public Message addWorker(@RequestBody AddWorkerRequest request) {
        return workerService.addWorker(request);
    }

    @PostMapping("/workers/editWorker")
    public Message editWorker(@RequestBody WorkerModel worker) {
        return workerService.editWorker(worker);
    }

}