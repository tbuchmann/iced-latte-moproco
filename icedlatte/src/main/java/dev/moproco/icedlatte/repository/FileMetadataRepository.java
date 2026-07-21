package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.FileMetadata;
import java.time.LocalDateTime;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
    java.util.Optional<FileMetadata> findByFileId(String fileId);
    java.util.Optional<FileMetadata> findByRelatedObjectId(String relatedObjectId);
    java.util.Optional<FileMetadata> findByBucketName(String bucketName);
    java.util.Optional<FileMetadata> findByFileName(String fileName);
    java.util.Optional<FileMetadata> findByCreatedAt(LocalDateTime createdAt);
    java.util.Optional<FileMetadata> findByUpdatedAt(LocalDateTime updatedAt);

}
