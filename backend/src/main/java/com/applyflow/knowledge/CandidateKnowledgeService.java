package com.applyflow.knowledge;

import com.applyflow.profile.ProfileService;
import com.applyflow.profile.ProfileViews;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
public class CandidateKnowledgeService {
  private final ProfileService profiles;
  private final ObjectProvider<VectorStore> vectorStores;
  public CandidateKnowledgeService(ProfileService profiles, ObjectProvider<VectorStore> vectorStores) {
    this.profiles = profiles;
    this.vectorStores = vectorStores;
  }
  public KnowledgeFact index(UUID profileId, UUID factId) {
    ProfileViews.Fact fact = profiles.verifiedFact(profileId, factId);
    store().add(List.of(new Document(fact.factText(), Map.of(
        "profileId", profileId.toString(), "factId", fact.id().toString(), "category", fact.category()))));
    return new KnowledgeFact(fact.id(), fact.category(), fact.factText(), fact.source());
  }
  public List<KnowledgeResult> search(UUID profileId, String query) {
    return store().similaritySearch(SearchRequest.builder().query(query).topK(5)
        .filterExpression("profileId == '" + profileId + "'").build()).stream()
        .map(document -> new KnowledgeResult(document.getText(), String.valueOf(document.getMetadata().get("factId")), String.valueOf(document.getMetadata().get("category"))))
        .toList();
  }
  private VectorStore store() {
    VectorStore store = vectorStores.getIfAvailable();
    if (store == null) throw new IllegalStateException("Vector store is unavailable; configure PostgreSQL, pgvector, and Gemini embeddings");
    return store;
  }
  public record KnowledgeFact(UUID factId, String category, String factText, String source) { }
  public record KnowledgeResult(String content, String factId, String category) { }
}

