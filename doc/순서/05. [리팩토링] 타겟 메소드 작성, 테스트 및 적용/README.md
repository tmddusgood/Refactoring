#순서
## 타겟 메소드 작성
### UserProjectMappingRepository
* UserProjectMappingRepositoryQueryDSL에 선언
~~~java
UserProjectMapping findByUserIdAndProjectIdOrThrowsException(Long userId, Long projectId);
~~~
* UserProjectMappingRepositoryImpl에서 구현
~~~java
    @Override
    public UserProjectMapping findByUserIdAndProjectIdOrThrowsException(Long userId, Long projectId){
        return Optional.ofNullable(queryFactory
                .select(userProjectMapping)
                .from(userProjectMapping)
                .where(userProjectMapping.user.userId.eq(userId), userProjectMapping.project.projectId.eq(projectId))
                .fetchFirst())
                .orElseThrow(() -> new ApiRequestException("해당 프로젝트에 소속된 유저가 아닙니다."));
    }
~~~
## 테스트
성공! (이미지 추가 예정)
## 적용
사진 추가 예정