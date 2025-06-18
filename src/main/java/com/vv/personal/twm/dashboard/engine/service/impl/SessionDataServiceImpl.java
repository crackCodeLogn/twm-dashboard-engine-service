package com.vv.personal.twm.dashboard.engine.service.impl;

import com.vv.personal.twm.dashboard.engine.dto.GetSessionDto;
import com.vv.personal.twm.dashboard.engine.dto.SessionDataDto;
import com.vv.personal.twm.dashboard.engine.model.Mode;
import com.vv.personal.twm.dashboard.engine.model.SessionData;
import com.vv.personal.twm.dashboard.engine.model.Subject;
import com.vv.personal.twm.dashboard.engine.service.ModeService;
import com.vv.personal.twm.dashboard.engine.service.SessionDataService;
import com.vv.personal.twm.dashboard.engine.service.SubjectService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Vivek
 * @since 2025-06-17
 */
@Slf4j
@AllArgsConstructor
@Service
public class SessionDataServiceImpl implements SessionDataService {
  private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyyMMdd");
  private static final String SESSION_DATA_FOLDER = "/home/v2/theTempest";

  private final ModeService modeService;
  private final SubjectService subjectService;

  @Override
  public Optional<SessionData> getSessionData(SessionDataDto sessionDto) {
    Optional<Subject> subject = subjectService.getSubject(sessionDto.getSubjectId());
    if (subject.isEmpty()) {
      log.error("Did not find subject for {}", sessionDto.getSubjectId());
      return Optional.empty();
    }
    Optional<Mode> mode = modeService.getMode(sessionDto.getModeId());
    if (mode.isEmpty()) {
      log.error("Did not find mode for {}", sessionDto.getModeId());
      return Optional.empty();
    }

    return Optional.of(
        SessionData.builder()
            .mode(mode.get())
            .student(sessionDto.getStudent())
            .subject(subject.get())
            .sessionDate(sessionDto.getSessionDate())
            .data(sessionDto.getData())
            .build());
  }

  @Override
  public void writeDataToLocalDisk(SessionData sessionData) {
    String file = getSessionDataFilePath(sessionData);
    try (PrintWriter writer = new PrintWriter(new File(file))) {
      writer.println(sessionData.getData());
    } catch (Exception e) {
      log.error("Failed to write data to local disk..", e);
    }
  }

  @Override
  public Optional<GetSessionDto> getLatestSessionData(String student) {
    File sessionDataFolder = new File(SESSION_DATA_FOLDER);
    if (sessionDataFolder.exists()) {
      File[] files = sessionDataFolder.listFiles();
      if (files != null) {
        Optional<File> latestStudentDataFile =
            Arrays.stream(files)
                .filter(file -> file.getName().contains(student))
                .max(Comparator.comparingLong(File::lastModified));
        if (latestStudentDataFile.isPresent()) {
          File sessionDataFile = latestStudentDataFile.get();

          StringBuilder data = new StringBuilder();
          try (BufferedReader reader = new BufferedReader(new FileReader(sessionDataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
              data.append(line.strip()).append("\n");
            }
          } catch (Exception e) {
            log.error("Failed to read data from local disk..", e);
          }

          return Optional.of(
              GetSessionDto.builder()
                  .fileData(data.toString())
                  .fileName(sessionDataFile.getName())
                  .build());
        }
      }
    }
    return Optional.empty();
  }

  private String getSessionDataFilePath(SessionData sessionData) {
    String mode = sessionData.getMode().getSessionMode().toLowerCase().strip();
    String fileTitleMode = "session";
    if (!mode.contains("varsity")) fileTitleMode += "-" + fileTitleMode;
    /*
    varsity => session
    direct => session-direct
    sinica => session-sinica
     */

    return String.format(
        "%s/%s.%s.%s.%s.txt",
        SESSION_DATA_FOLDER,
        fileTitleMode,
        sessionData.getStudent(),
        sessionData.getSubject().getSessionSubject(),
        sessionData.getSessionDate().format(DTF));
  }
}
