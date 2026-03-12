
import org.itacademy.domain.exception.RequestNotFoundException;
import org.itacademy.domain.exception.TaskAlreadyCompletedException;
import org.itacademy.domain.task.dto.TaskDtoRequest;
import org.itacademy.domain.task.dto.TaskDtoResponse;
import org.itacademy.domain.task.model.Priority;
import org.itacademy.domain.task.model.Status;
import org.itacademy.domain.task.model.Task;
import org.itacademy.domain.task.repository.TaskRepository;
import org.itacademy.domain.task.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    private TaskRepository taskRepository;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class); // el mock NO puede ser null
        taskService = new TaskService(taskRepository);
    }

    @Test
    void shouldCreateTask_whenRequestIsValid() {

        TaskDtoRequest request = new TaskDtoRequest(
                "Study",
                "Mockito practice",
                LocalDate.now().plusDays(2),
                Priority.HIGH,
                Status.NOT_COMPLETED,
                1L
        );

        Task savedTask = new Task(
                1L,
                request.getTitle(),
                request.getDescription(),
                LocalDate.now(),
                request.getDeadline(),
                request.getPriority(),
                Status.NOT_COMPLETED,
                request.getEventId()
        );
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        var response = taskService.createTask(request);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(TaskDtoResponse.class, response.getClass()),
                () -> assertEquals(request.getTitle(), response.getTitle())
        );

        verify(taskRepository).save(any(Task.class));
    }


    @Test
    void shouldReturnAllTasks() {

        Task task1 = new Task(
                1L, "Task1", "Desc1",
                LocalDate.now(), null,
                Priority.MIDDLE, Status.NOT_COMPLETED, 1L
        );

        Task task2 = new Task(
                2L, "Task2", "Desc2",
                LocalDate.now(), null,
                Priority.LOW, Status.NOT_COMPLETED, 1L
        );


        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));
        var result = taskService.listTasks();


        assertEquals(2, result.size());
        verify(taskRepository).findAll();
    }


    @Test
    void shouldMarkTaskAsCompleted_whenTaskExists() {

        Task task = new Task(
                1L,
                "Study",
                "Mockito",
                LocalDate.now(),
                null,
                Priority.HIGH,
                Status.NOT_COMPLETED,
                1L
        );
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.markAsCompleted(1L);

        assertEquals(Status.COMPLETED, task.getStatus());
        verify(taskRepository).update(task);
    }


    @Test
    void shouldThrowException_whenTaskNotFound() {

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                RequestNotFoundException.class,
                () -> taskService.markAsCompleted(1L)
        );
        verify(taskRepository).findById(1L);
    }


    @Test
    void shouldThrowException_whenTaskAlreadyCompleted() {

        Task task = new Task(
                1L,
                "Study",
                "Mockito",
                LocalDate.now(),
                null,
                Priority.HIGH,
                Status.COMPLETED,
                1L
        );

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        assertThrows(
                TaskAlreadyCompletedException.class,
                () -> taskService.markAsCompleted(1L)
        );
        verify(taskRepository).findById(1L);
    }


    @Test
    void shouldReturnCompletedTasks() {

        Task task1 = new Task(
                1L, "Task1", "Desc",
                LocalDate.now(), null,
                Priority.HIGH, Status.COMPLETED, 1L
        );

        Task task2 = new Task(
                2L, "Task2", "Desc",
                LocalDate.now(), null,
                Priority.LOW, Status.COMPLETED, 1L
        );
        when(taskRepository.findByStatus()).thenReturn(List.of(task1, task2));

        var result = taskService.listCompletedTasks();

        assertEquals(2, result.size());
        verify(taskRepository).findByStatus();
    }


    @Test
    void shouldDeleteTask_whenTaskExists() {

        Task task = new Task(
                1L,
                "Study",
                "Mockito",
                LocalDate.now(),
                null,
                Priority.HIGH,
                Status.NOT_COMPLETED,
                1L
        );
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.deleteTask(1L);

        verify(taskRepository).deleteById(1L);
    }


    @Test
    void shouldThrowException_whenDeletingNonExistingTask() {

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                RequestNotFoundException.class,
                () -> taskService.deleteTask(1L)
        );

        verify(taskRepository).findById(1L);
    }
}
