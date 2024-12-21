package wh.duckbill.netflix.token;

public interface UpdateTokenUsecase {
  String upsertToken(String providerId);
}
