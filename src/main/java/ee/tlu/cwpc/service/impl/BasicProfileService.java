package ee.tlu.cwpc.service.impl;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanConstructorResultTransformer;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ee.tlu.cwpc.dao.Crud;
import ee.tlu.cwpc.dto.ProfileDTO;
import ee.tlu.cwpc.model.Keyword;
import ee.tlu.cwpc.model.Profile;
import ee.tlu.cwpc.model.Url;
import ee.tlu.cwpc.service.ProfileService;

@Service("profileService")
public class BasicProfileService implements ProfileService {

  @Autowired
  private Crud crud;

  @Override
  @Transactional
  public ProfileDTO getProfile(long id) {
    DetachedCriteria criteria =
        DetachedCriteria.forClass(Profile.class).add(Restrictions.eq("id", id))
            .setProjection(Projections.projectionList().add(Projections.property("id"))
                .add(Projections.property("name")).add(Projections.property("created"))
                .add(Projections.property("updated")));
    criteria.setResultTransformer(
        new AliasToBeanConstructorResultTransformer(ProfileDTO.class.getConstructors()[0]));
    List<ProfileDTO> profile = crud.find(criteria);
    return profile.get(0);
  }

  @Override
  @Transactional
  public List<ProfileDTO> getProfiles() {
    DetachedCriteria criteria = DetachedCriteria.forClass(Profile.class)
        .setProjection(Projections.projectionList().add(Projections.property("id"))
            .add(Projections.property("name")).add(Projections.property("created"))
            .add(Projections.property("updated")));
    criteria.setResultTransformer(
        new AliasToBeanConstructorResultTransformer(ProfileDTO.class.getConstructors()[0]));
    return crud.find(criteria);
  }

  @Override
  @Transactional
  public void createProfile(String name, List<String> urls, List<String> keywords) {
    DateTime date = DateTime.now();
    Profile profile = new Profile();
    profile.setName(name);
    urls.stream().forEach(url -> profile.addUrl(new Url(url, date, date)));
    keywords.stream().forEach(keyword -> profile.addKeyword(new Keyword(keyword, date, date)));
    crud.save(profile);
  }

  @Override
  @Transactional
  public void updateProfile(long id, String name, List<String> keywords) {
    DateTime date = DateTime.now();
    Profile profile = crud.get(id, Profile.class);
    profile.setName(name);
    profile.getKeywords().clear();
    keywords.stream().forEach(keyword -> profile.addKeyword(new Keyword(keyword, date, date)));
    crud.save(profile);
  }

  @Override
  @Transactional
  public void deleteProfile(long id) {
    crud.delete(id, Profile.class);
  }

  @Override
  @Transactional
  public List<String> getKeywordsByProfileId(long profileId) {
    DetachedCriteria criteria = DetachedCriteria.forClass(Keyword.class, "k")
        .createAlias("k.profile", "profile").add(Restrictions.eq("profile.id", profileId))
        .setProjection(Projections.property("keyword"));
    return crud.find(criteria);
  }

  @Override
  @Transactional
  public List<String> getUrlsByProfileId(long profileId) {
    DetachedCriteria criteria = DetachedCriteria.forClass(Url.class, "u")
        .createAlias("u.profile", "profile").add(Restrictions.eq("profile.id", profileId))
        .setProjection(Projections.property("url"));
    return crud.find(criteria);
  }

}
